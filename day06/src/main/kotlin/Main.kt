
fun String.findFirstMarker(size: Int = 4) =
    windowed(size,1).indexOfFirst { it.toSet().size == size } + size
