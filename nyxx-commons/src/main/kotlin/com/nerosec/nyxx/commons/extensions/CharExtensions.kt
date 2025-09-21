package com.nerosec.nyxx.commons.extensions

import com.nerosec.nyxx.commons.CharacterType

object CharExtensions {

    fun Char.getType(): CharacterType {
        return if (isLetter()) if (isUpperCase()) CharacterType.UC_LETTER else CharacterType.LC_LETTER
        else if (isDigit()) CharacterType.DIGIT
        else CharacterType.SPECIAL
    }
}