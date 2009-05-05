/*
 * Copyright 2008 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package samples.junit4.annotationbased;

import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.annotation.MockNice;
import org.powermock.api.easymock.annotation.MockStrict;
import org.powermock.api.easymock.powermocklistener.AnnotationEnabler;
import org.powermock.core.classloader.annotations.PowerMockListener;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import samples.finalmocking.FinalDemo;

/**
 * Test class to demonstrate non-static final mocking with one listeners
 * injecting mocks to fields annotated with {@link MockNice}.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(FinalDemo.class)
@PowerMockListener(AnnotationEnabler.class)
public class FinalDemoWithStrictAnnotationInjectionTest {

	@MockStrict
	private FinalDemo tested;

	@Test
	public void testMockStrictOk() throws Exception {
		String expected = "Hello altered World";

		expect(tested.say("hello")).andReturn(expected);
		expect(tested.say("hello2")).andReturn(expected);

		replay(tested);

		String actual = tested.say("hello");
		String actual2 = tested.say("hello2");

		verify(tested);
		assertEquals("Expected and actual did not match", expected, actual);
		assertEquals("Expected and actual did not match", expected, actual2);
	}

	@Test(expected = AssertionError.class)
	public void testMockStrictNotOk() throws Exception {
		String expected = "Hello altered World";

		expect(tested.say("hello")).andReturn(expected);

		replay(tested);

		tested.say("hello2");
	}
}
