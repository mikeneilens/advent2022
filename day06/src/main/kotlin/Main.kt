
fun String.findFirstMarker(size: Int = 4) =
    windowed(size,1).indexOfFirst { it.toSet().toList() == it.toList() } + size
