package com.example.readingquestsfun.repository

import android.media.audiofx.DynamicsProcessing.Eq
import com.example.readingquestsfun.R
import com.example.readingquestsfun.models.EquipmentModel
import java.util.Random

class EquipmentRepository() {

    fun createEquipmentSet(): List<EquipmentModel> {
        val listOfEquipment = mutableListOf<EquipmentModel>()

        listOfEquipment.add(
            EquipmentModel(
                id = 1,
                name = "Яблоко",
                icon = R.drawable.equip_apple,
                description = "Можно съесть",
                quantity = 3
            )
        )

        listOfEquipment.add(
            EquipmentModel(
                id = 2,
                name = "Банан",
                icon = R.drawable.equip_banana,
                description = "Можно съесть",
                quantity = 4
            )
        )

        listOfEquipment.add(
            EquipmentModel(
                id = 3,
                name = "Кость",
                icon = R.drawable.equip_bone,
                description = "Человеческая кость, зачем она тебе - загадка",
                quantity = 1
            )
        )

        listOfEquipment.add(
            EquipmentModel(
                id = 4,
                name = "Дневник",
                icon = R.drawable.equip_book,
                description = "Твой дневник, возможно, самое ценное, что у тебя есть",
                quantity = 1
            )
        )

        listOfEquipment.add(
            EquipmentModel(
                id = 5,
                name = "Монеты",
                icon = R.drawable.equip_coin,
                description = "Очевидно, что на них можно что-то купить",
                quantity = 200
            )
        )

        listOfEquipment.add(
            EquipmentModel(
                id = 6,
                name = "Рыба",
                icon = R.drawable.equip_fish,
                description = "Удачный улов, можно съесть",
                quantity = 1
            )
        )

        listOfEquipment.add(
            EquipmentModel(
                id = 7,
                name = "Ключ",
                icon = R.drawable.equip_key,
                description = "Старый ключ. Где-тое сть замок от него",
                quantity = 1
            )
        )

        listOfEquipment.add(
            EquipmentModel(
                id = 8,
                name = "Зелье",
                icon = R.drawable.equip_potion,
                description = "Очень сильное зелье",
                quantity = 3
            )
        )

        listOfEquipment.add(
            EquipmentModel(
                id = 9,
                name = "Свиток",
                icon = R.drawable.equip_scroll,
                description = "Загадочный свиток с непонятными надписями",
                quantity = 1
            )
        )

        listOfEquipment.add(
            EquipmentModel(
                id = 10,
                name = "Меч",
                icon = R.drawable.equip_sword,
                description = "Очевидно, им можно кого-то поранить",
                quantity = 1
            )
        )

        return listOfEquipment
    }
}