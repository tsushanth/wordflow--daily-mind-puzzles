package com.factory.wordflowdailymindpuzzles.data

object WordBank {
    val scrambleWords: List<String> = listOf(
        "APPLE", "BEACH", "CLOUD", "DANCE", "EAGLE", "FLAME", "GRAPE", "HOUSE", "IMAGE", "JELLY",
        "LEMON", "MONEY", "NIGHT", "OCEAN", "PIANO", "QUEEN", "RIVER", "STONE", "TIGER", "UNITY",
        "VOICE", "WATER", "YOUTH", "BREAD", "CANDY", "DREAM", "EARTH", "FRUIT", "GARDEN", "HONEY",
        "ISLAND", "JACKET", "KITTEN", "LADDER", "MARKET", "NATURE", "ORANGE", "PENCIL", "RABBIT",
        "SILVER", "TURTLE", "VELVET", "WINTER", "YELLOW", "ANCHOR", "BASKET", "CASTLE", "DOLPHIN",
        "ELEPHANT", "FEATHER", "GALAXY", "HARVEST", "JOURNEY", "KINGDOM", "LANTERN", "MEADOW",
        "NOTEBOOK", "ORCHARD", "PUZZLE", "RAINBOW", "SUNSET", "TREASURE", "VOLCANO", "WHISPER",
        "CRYSTAL", "DIAMOND", "ECLIPSE", "FOUNTAIN", "GLACIER", "HORIZON", "ICEBERG", "JASMINE",
        "KOALA", "LAGOON", "MYSTERY", "NEBULA", "OASIS", "PELICAN", "QUARTZ", "RIPPLE", "SPARROW",
        "TORNADO", "UNICORN", "CANYON", "DESERT", "FALCON", "GRANITE", "HAMMOCK", "IGLOO", "JUNGLE",
        "KETTLE", "LAVENDER", "MARBLE", "NECTAR", "OYSTER", "PEACOCK", "QUIVER", "ROCKET", "SATURN",
        "THUNDER", "URCHIN", "VIOLET", "WALNUT", "PLANET", "COMET", "METEOR"
    )

    val wordSearchCategories: Map<String, List<String>> = mapOf(
        "Animals" to listOf("CAT", "LION", "TIGER", "ZEBRA", "PANDA", "EAGLE", "RABBIT", "DOLPHIN"),
        "Nature" to listOf("RIVER", "MOUNTAIN", "FOREST", "OCEAN", "DESERT", "VOLCANO", "GLACIER", "CANYON"),
        "Food" to listOf("PIZZA", "BURGER", "PASTA", "SALAD", "COOKIE", "MANGO", "BANANA", "CHEESE"),
        "Space" to listOf("PLANET", "COMET", "GALAXY", "ROCKET", "METEOR", "ORBIT", "NEBULA", "SATURN")
    )
}
