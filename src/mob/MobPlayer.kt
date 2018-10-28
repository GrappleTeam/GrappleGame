package mob

import ResourceLoader

class MobPlayer internal constructor(
        damage: Int,
        health: Int,
        x: Int,
        y: Int
) : Mob("Player", damage, health, x, y) {

    init {
        this.sprite = ResourceLoader.getImage("girl10.png")
    }
}
