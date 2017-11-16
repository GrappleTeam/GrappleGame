class MobPlayer internal constructor(
        damage: Int,
        health: Int,
        x: Int,
        y: Int
) : Mob("Player", damage, health, x, y) {

    init {
        this.sprite = ImageUtils.getImage("graphics/girl10.png")
    }
}