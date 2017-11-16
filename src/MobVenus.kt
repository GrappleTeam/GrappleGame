class MobVenus(
        name: String?,
        damage: Int,
        health: Int,
        x: Int,
        y: Int
) : Mob(name, damage, health, x, y) {

    val MAX_HEALTH = 2
//    private var damage = 3
//    private var health = 5

    init {
//        if (health > MAX_HEALTH){
//            health = MAX_HEALTH
//        }
        this.sprite = ImageUtils.getImage("graphics/Plant02.png")
    }

    internal constructor(x: Int, y: Int) : this("Venus", 0, 5, x, y) {
        this.sprite = ImageUtils.getImage("graphics/Plant02.png")
    }

    private var mod = 2
    private var tic = 0

    override fun pattern() {
        if (tic > 50) {
            mod = -mod
            tic = 0
        }
        setXspeed(mod)
        move()
        tic++
    }
}

