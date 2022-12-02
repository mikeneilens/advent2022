typealias FixturePattern = List<Int?>

val fixturePattern:FixturePattern = listOf(0,null,1,2,3,4,5,null,6,7)

fun <Product>addAllProductsToFixtures(products:List<Product>) = products
    .chunked(8)
    .flatMap{fixturePattern.addProductsToFixture(it)}
    .chunked(2)
    .map{it.filterNotNull()}
    .filter{it.isNotEmpty()}

fun <Product>FixturePattern.addProductsToFixture(products: List<Product>) = map{ productNdx -> getProductForPosition(productNdx, products)}

fun <Product>getProductForPosition(productNdx: Int?, products: List<Product>) =
    if (productNdx != null && products.getOrNull(productNdx) != null) products[productNdx] else null
