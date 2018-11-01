package common

class Tile(
        override var x: Int,
        override var y: Int,
        override var image: String?
) : ITile {
    enum class Type {
        DOOR, GRASS, TOPRIGHT, TOPLEFT, HORIZONTAL, BOTTOMLEFT, BOTTOMRIGHT, BOTTOMHORIZONTAL,
    }
}
