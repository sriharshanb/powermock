package samples.junit4.classwithinnermembers;

import static org.junit.Assert.assertEquals;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.expectNew;
import static org.powermock.api.easymock.PowerMock.expectPrivate;
import static org.powermock.api.easymock.PowerMock.replayAll;
import static org.powermock.api.easymock.PowerMock.verifyAll;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import samples.classwithinnermembers.ClassWithInnerMembers;

/**
 * Test PowerMock's basic support for inner (member) and local classes.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(ClassWithInnerMembers.class)
public class ClassWithInnerMembersTest {

	@Test
	public void assertStaticMemberClassMockingWorksWithNoConstructorArguments() throws Exception {
		Class<Object> innerClassType = Whitebox.getInnerClassType(ClassWithInnerMembers.class, "MyInnerClass");
		Object innerClassMock = createMock(innerClassType);
		expectNew(innerClassType).andReturn(innerClassMock);
		expectPrivate(innerClassMock, "doStuff").andReturn("something else");

		replayAll();

		assertEquals("something else", new ClassWithInnerMembers().getValue());

		verifyAll();
	}

	@Test
	public void assertStaticMemberClassMockingWorksWithConstructorArguments() throws Exception {
		Class<Object> innerClassType = Whitebox.getInnerClassType(ClassWithInnerMembers.class, "StaticInnerClassWithConstructorArgument");
		Object innerClassMock = createMock(innerClassType);
		expectNew(innerClassType, "value").andReturn(innerClassMock);
		expectPrivate(innerClassMock, "doStuff").andReturn("something else");

		replayAll();

		assertEquals("something else", new ClassWithInnerMembers().getValueForStaticInnerClassWithConstructorArgument());

		verifyAll();
	}

	@Test
	public void assertLocalClassMockingWorks() throws Exception {

		final Class<Object> type = Whitebox.getLocalClassType(ClassWithInnerMembers.class, 1, "MyLocalClass");
		Object innerClassMock = createMock(type);
		expectNew(type).andReturn(innerClassMock);
		expectPrivate(innerClassMock, "doStuff").andReturn("something else");

		replayAll();

		assertEquals("something else", new ClassWithInnerMembers().getLocalClassValue());

		verifyAll();
	}

	@Test
	public void assertLocalClassMockingWorksWithArguments() throws Exception {
		final Class<Object> type = Whitebox.getLocalClassType(ClassWithInnerMembers.class, 2, "MyLocalClass");
		Object innerClassMock = createMock(type);
		expectNew(type, "my value").andReturn(innerClassMock);
		expectPrivate(innerClassMock, "doStuff").andReturn("something else");

		replayAll();

		assertEquals("something else", new ClassWithInnerMembers().getLocalClassValueWithArgument());

		verifyAll();
	}

	@Test
	public void assertNonStaticMemberClassMockingWorksWithArguments() throws Exception {
		Class<Object> innerClassType = Whitebox.getInnerClassType(ClassWithInnerMembers.class, "MyInnerClassWithConstructorArgument");
		Object innerClassMock = createMock(innerClassType);
		expectNew(innerClassType, "value").andReturn(innerClassMock);
		expectPrivate(innerClassMock, "doStuff").andReturn("something else");

		replayAll();

		assertEquals("something else", new ClassWithInnerMembers().getValueForInnerClassWithConstructorArgument());

		verifyAll();
	}

	@Test
	public void assertThatAnonymousInnerClassesWorks() throws Exception {
		final Class<Object> type = Whitebox.getAnonymousInnerClassType(ClassWithInnerMembers.class, 1);
		Object innerClassMock = createMock(type);
		expectNew(type).andReturn(innerClassMock);
		expectPrivate(innerClassMock, "doStuff").andReturn("something else");

		replayAll();

		assertEquals("something else", new ClassWithInnerMembers().getValueForAnonymousInnerClass());

		verifyAll();
	}
}
