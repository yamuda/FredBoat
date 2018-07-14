package fredboat.command.moderation

import com.fredboat.sentinel.entities.ModRequestType
import fredboat.testutil.sentinel.DefaultSentinelRaws
import fredboat.testutil.sentinel.SentinelState
import fredboat.testutil.sentinel.assertReply
import fredboat.testutil.sentinel.assertReplyContains
import org.junit.jupiter.api.Test

class HardbanCommandTest : AbstractModerationTest() {

    @Test
    fun testWorking() {
        testCommand(message = ";;ban realkc Generic shitposting", invoker = DefaultSentinelRaws.napster) {
            expect(ModRequestType.BAN, DefaultSentinelRaws.realkc, "Generic shitposting")
        }
    }

    @Test
    fun testWorkingOwner() {
        testCommand(message = ";;ban napster Generic shitposting", invoker = DefaultSentinelRaws.owner) {
            expect(ModRequestType.BAN, DefaultSentinelRaws.napster, "Generic shitposting")
        }
    }

    @Test
    fun testUnprivileged() {
        testCommand(message = ";;ban napster Generic shitposting", invoker = DefaultSentinelRaws.realkc) {
            assertReply("You need the following permission to perform that action: **Ban Members**")
        }
    }

    @Test
    fun testBotUnprivileged() {
        SentinelState.setRoles(member = DefaultSentinelRaws.self, roles = emptyList())
        testCommand(message = ";;ban realkc Generic shitposting", invoker = DefaultSentinelRaws.napster) {
            assertReplyContains("I need to have a higher role than ${DefaultSentinelRaws.realkc.nickname}")
        }
    }

}