package common

open class Block(
        override var x: Int = 0,
        override var y: Int = 0,
        override var width: Int = 0,
        override var height: Int = 0,
        final override val type: Type = Type.PLAIN
) : IBlock {
    override var image: String? = when (type) {
        Type.PLAIN -> "BrickBlock01.png"
        Type.DEATH -> "DeathBlock01.png"
        Type.PLATFORM -> "PlatformBlock01.png"
        Type.PORTAL -> "Portal01.png"
        Type.SLOW -> "SlowBlock01.png"
    }
    override val isPlatform: Boolean
        get() {
            return type == Type.PLATFORM
        }

    internal constructor(x: Int, y: Int, r: Block) :
            this(x, y, r.width, r.height, r.type)

    override fun withinBounds(testX: Int, testY: Int, testWidth: Int, testHeight: Int): Boolean = (x < testX + testWidth
            && testX < x + width
            && y < testY + testHeight
            && testY < y + height)

    enum class Type {
        DEATH, PLAIN, PLATFORM, PORTAL, SLOW
    }
}
