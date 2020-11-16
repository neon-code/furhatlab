package furhatos.app.fruitseller.flow

import furhatos.app.fruitseller.nlu.*
import furhatos.flow.kotlin.*
import furhatos.nlu.common.*
import furhatos.nlu.SimpleIntent
import furhatos.util.Language

val Start : State = state(Interaction) {

    onEntry {
        furhat.ask("Hello There!")
    }

    //Star Wars Episode III easter egg
    onResponse(GeneralKenobi) {
        furhat.say("You are a bold one!")
        delay(2000)
        goto(TakingOrder)
    }

    //If user asks for fruit directly
    onResponse<BuyFruit> {
        furhat.say("${it.intent.fruit}, what a lovely choice!")
    }

    //Normal conversation, back to the normal assignment
    onResponse {
        goto(TakingOrder)
    }
}

val TakingOrder = state {
    onEntry {
        random(
                { furhat.ask("I hope you are well, how about some fruits?") },
                { furhat.ask("I hope you are well, do you want some fruits?") }
        )
    }

    onReentry {
        random(
                { furhat.ask("What kind of fruit do you want?") },
                { furhat.ask("What type of fruit?") }
        )
    }

    onResponse<Yes> {
        random(
                { furhat.ask("What kind of fruit do you want?") },
                { furhat.ask("What type of fruit?") }
        )
    }

    onResponse<No> {
        furhat.say("Okay, that's a shame. Have a splendid day!")
        goto(Idle)
    }

    onResponse<RequestOptions> {
        furhat.say("We have ${Fruit().optionsToText()}")
        furhat.ask("Do you want some?")
    }

    onResponse<BuyFruit> {
        furhat.say("${it.intent.fruit}, what a lovely choice!")
    }
}


