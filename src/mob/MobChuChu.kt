package mob

import resources.graphics.ImageUtils

class MobChuChu(
        name: String,
        damage: Int,
        health: Int,
        x: Int,
        y: Int
) : Mob(name, damage, health, x, y) {

    private var speedmod = 4
    private var tic = 0

    init {
        this.sprite = ImageUtils.getImage("Sprout01.png")
    }

    internal constructor(x: Int, y: Int) : this("ChuChu", damage, 5, x, y)
    internal constructor(damage: Int, health: Int, x: Int, y: Int) : this("ChuChu", damage, health, x, y)

    override fun pattern() {
        jump()
        if (tic > 100) {
            speedmod = -speedmod
            tic = 0
        }
        xspeed = speedmod
        move()
        tic++
    }

    companion object {
        private val damage = 3
        private val health = 5
    }
}
