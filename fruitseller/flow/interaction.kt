package furhatos.app.fruitseller.flow

import furhatos.app.fruitseller.nlu.*
import furhatos.flow.kotlin.*
import furhatos.nlu.common.*
import furhatos.util.Language
import furhatos.nlu.SimpleIntent

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
        val fruits = it.intent.fruits
        furhat.say("${fruits!!.text}, what a lovely choice!")
        fruits!!.list.forEach {
            users.current.order.fruits.list.add(it)
        }
    }

    //Normal conversation, back to the normal assignment
    onResponse {
        goto(TakingOrder)
    }
}

// Parent state for eliminating repetitive coding
val Options = state(Interaction) {
    onResponse<BuyFruit> {
        val fruits = it.intent.fruits
        if (fruits != null) {
            goto(OrderReceived(fruits))
        } else {
            propagate()
        }
    }

    onResponse<RequestOptions> {
        furhat.say("We have ${Fruit().optionsToText()}")
        furhat.ask("Do you want some?")
    }

    onResponse<Yes> {
        random(
                { furhat.ask("What kind of fruit do you want?") },
                { furhat.ask("What type of fruit?") }
        )
    }
}

val TakingOrder = state(parent = Options) {
    onEntry {
        random(
                { furhat.ask("I hope you are well, how about some fruits?") },
                { furhat.ask("I hope you are well, do you want some fruits?") }
        )
    }
    /*
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
    */
    onResponse<No> {
        furhat.say("Okay, that's a shame. Have a splendid day!")
        goto(Idle)
    }
    /*
    onResponse<RequestOptions> {
        furhat.say("We have ${Fruit().optionsToText()}")
        furhat.ask("Do you want some?")
    }

    onResponse<BuyFruit> {
        val fruits = it.intent.fruits
        if(fruites != null) {
            goto(OrderReceived(fruits))
        } else {
            propagate()
        }
       /*
        * Old version of BuyFruit
        furhat.say("${fruits.text}, what a lovely choice!")
        fruits.list.forEach {
            users.current.order.fruits.list.add(it)

        }
        */

    }
    */
}

fun OrderReceived(fruits: FruitList) : State = state(Options) {
    onEntry {
        furhat.say("${fruits.text}, what a lovely choice!")
        fruits.list.forEach{
            users.current.order.fruits.list.add(it)
        }
        furhat.ask("Anything else?")
    }

    onReentry {
        furhat.ask("Did you want something else?")
    }
    /*
    onResponse<BuyFruit> {
        val fruits = it.intent.fruits
        if(fruits != null){
            goto(OrderReceived(fruits))
        } else {
            propagate()
        }
    }

    onResponse<Yes> {
        random(
                {furhat.ask("What kind of fruit do you want?")},
                {furhat.ask("What type of fruit?")}
        )
    }
    */
    onResponse<No> {
        furhat.say("Okay, here is your order of ${users.current.order.fruits}. Have a great day!")
        goto(Idle)
    }
}

