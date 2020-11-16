package furhatos.app.fruitseller.nlu

import furhatos.nlu.*
import furhatos.nlu.grammar.Grammar
import furhatos.nlu.kotlin.grammar
import furhatos.nlu.common.Number
import furhatos.util.Language

//Easter egg class
val GeneralKenobi = SimpleIntent("General Kenobi" )

// Our Fruit entity.
class Fruit : EnumEntity(stemming = true, speechRecPhrases = true) {
    override fun getEnum(lang: Language): List<String> {
        return listOf("banana", "orange", "apple", "grapes", "strawberry", "pineapple")
    }
}

// Our BuyFruit intent
class BuyFruit(val fruit : Fruit? = null) : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf("@fruit", "I would like an apple", "I want to buy a @fruit", "I want a @fruit")
    }
}

//User asks for options
class RequestOptions: Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf("What options do you have?",
                "What fruits do you have?",
                "What are the alternatives?",
                "What do you have?")
    }
}