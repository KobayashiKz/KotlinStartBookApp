package com.kk.kotlinstartbookapp.kotlinstartbookapp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}

// 有理数クラスの定義
// 有理数:分数
class Rational(val n: Int, val d: Int) {
    // n, d：プライマリコンストラクタ

    private val g = gcd(n, d)
    val numerator: Int = n / g
    val denominator: Int = d / g

    // メソッドをイコールで結ぶこともできる
    // 1行ではかなり簡潔に書ける
    override fun toString(): String = "$numerator/$denominator"

    // イニシャライザ
    // コンストラクタを受け取って初めの処理
    init {
        // require(): 要求に反した場合はIllegalArgumentExceptionが発生する
        require(denominator != 0) {"denominator must not be null"}
    }

    // 最大公約数を返すメソッド
    // tailrec: 再帰呼び出しを可能にする. gcd -> gcd...
    private tailrec fun gcd(a: Int, b: Int): Int =
            if (b == 0) a
            else gcd(b, a % b)
}

// 演算子オーバーロード
// メソッドオーバーロード
// 拡張関数 など後ほど詳細出てくる
class Rational2(n: Int, d: Int) {
    init {
        require(d != 0){"denominator must not be null"}
    }

    private val g by lazy { gcd(Math.abs(n), Math.abs(d)) }
    val numerator: Int by lazy { n / g }
    val denominator: Int by lazy { d / g }

    operator fun plus(that: Rational2): Rational2 =
            Rational2(
                numerator * that.denominator + that.numerator * denominator,
                denominator * that.denominator
            )

    override fun toString(): String = "$numerator/$denominator"

    private tailrec fun gcd(a: Int, b: Int): Int =
            if (b == 0) a
            else gcd(b, a % b)
}


/**
 * 第4章 基本的な文法
 */
// 静的型付けをしなくとも型推論ができる

// 配列
val strs = arrayOf("a", "b")
// 不変（イミュータブル）リスト
val list: List<Int> = listOf(1, 2)
// 変更可能（ミュータブル）リスト
val mutableList: MutableList<Int> = mutableListOf(1, 2)
// セット：集合を表すコレクション. 重複がない. 要素の順序がない
// イミュータブルセット
val set: Set<Int> = setOf(1, 2)
// ミュータブルセット
val mutableSet: MutableSet<Int> = mutableSetOf(1, 2)
// マップ：keyとvalue
val map: Map<String, String> = mapOf("key" to "value")
// ミュータブルマップ
val mutableMap: MutableMap<String, Int> = mutableMapOf("one" to 1, "two" to 2)

// レンジ：範囲を表す
val range: IntRange = 12..15

fun test() {
    val result: Boolean
    // レンジ内かどうかを調べる
    if (5 in range) result = true

    // レンジをリストに変換
    range.toList()
    // 15から12へ減っていくリスト
    // reversed()：逆にする
    range.reversed().toList()
    // 2の増え幅で上がっていく
    // step: 変化幅
    (1..5 step 2).toList()
}

// if文
val score = 65
// 直接変数に代入できる
val message = if (score >= 60) "合格" else "不合格"
// else-ifを繋げることができる
val grade =
    if (score >= 90) "A"
    else if (score >= 80) "B"
    else if (score >= 60) "C"
    else "D"

// when: switchを強化したもの
val x = 1
val str = when (x) {
    1 -> "one"
    2, 3 -> "two or three"
    else -> "other"
}

// 関数も指定できるなど様々な分岐がwhenでできる
val whenaPractice = when (x) {
    1 -> "one"
    hoge() -> "hoge"
    in 2..4 -> "two - four"
    else -> "other"
}
fun hoge(): Int {
    return 4
}

// while, do-whileも記述できる
// for文: @でラベルを指定できラベルジャンプできる
fun forPrac() {
    loop@for (x in list) {
        // break, continue
        // break@ラベル名, continue@ラベル名 でラベルへジャンプできる
        break@loop
    }
}

/**
 * 第5章 関数
 */
// 以下のような1行での関数定義も可能
fun succ(i: Int): Int = i + 1

// デフォルト引数
// 指定しておくと引数省略することもできる
fun hello(name: String = "World"): String {
    return "Hello $name"
}

// 可変長引数
// varargを引数につけるだけで可変長になる
fun sum(vararg ints: Int): Int {
    var sum = 0
    for (i in ints) {
        sum += i
    }
    return sum
}
fun fuga() {
    // 可変長引数メソッドはこんな感じで呼び出せる
    var i = sum(1, 2, 3)
}

// 再帰呼び出し: 関数が自分自身を呼び出すこと
fun sum(numbers: List<Long>): Long =
        // リストが空の場合は0で返す
        if (numbers.isEmpty()) 0
        // リストの最初と先頭を落としたリストを合計する
        // これを繰り返すことでリストの合計を求めることができる
        else numbers.first() + sum(numbers.drop(1))

// ローカル関数: 関数定義の中に別関数を入れることができる
// 主にスコープを制限したいときに用いられる
fun sumLocal(numbers: List<Long>): Long {
    tailrec fun go(numbers: List<Long>, accumulator: Long): Long =
            if (numbers.isEmpty()) accumulator
            else go(numbers.drop(1), accumulator + numbers.first())
    return go(numbers, 0)
}

// 結果を返さない関数
// Unitで定義しておけば問題ない
fun countUp(): Unit {
}

/**
 * 第6章 第1級オブジェクトとしての関数
 */
// 第1級オブジェクト: 関数のようにふるまう関数オブジェクト

// 関数オブジェクト: 関数をオブジェクトのように扱える
// ::を使用することで関数オブジェクトが取得できる
// オブジェクトにパラメーターを指定するような感覚で関数オブジェクトに引数を渡せる
fun square(i: Int): Int = i * i

fun main(args: Array<String>) {
    val functionObject = ::square
    println(functionObject(5))
}

// 関数型
// 関数オブジェクトにも型が存在する
fun hogehoge() {
    // (Int) -> Int が型になる
    // (引数1, 引数2 ...) -> 戻り値
    val functionObject: (Int) -> Int = ::square
}

// 高階関数: 関数の引数として与えたり、引数として返ることができる関数のこと
// 関数の抽象化が可能である
// 変数部分に関数を埋め込むことができるイメージ

// 与えられた文字列の中で、Kが出てくるインデックス値を返す関数
fun firstK(str: String): Int {
    // tailrecで再帰呼び出し可能にする
    tailrec fun go(str: String, index: Int): Int =
            when {
                str.isEmpty() -> -1
                str.first().equals("K") -> index
                else -> go(str.drop(1), index + 1)
            }
    return go(str, 0)
}

// 与えられた文字列の中で、最初に大文字がでてくるインデックス値
fun firstUpper(str: String): Int {
    tailrec fun go(str: String, index: Int): Int =
            when {
                str.isEmpty() -> -1
                str.first().isUpperCase() -> index
                else -> go(str.drop(1), index + 1)
            }
    return go(str, 0)
}

// firstK()とfirstUpper()の違いは、Kか大文字かの違いだけ.
// 再利用できる形にできるとスマートになる

// predicateは (Char) -> Boolean型の関数オブジェクト
fun first(str: String, predicate: (Char) -> Boolean): Int {
    tailrec fun go(str: String, index: Int): Int =
            when {
                str.isEmpty() -> -1
                predicate(str.first()) -> index
                else -> go(str.drop(1), index + 1)
            }
    return go(str, 0)
}

fun firstKfun(str: String): Int {
    // (Char) -> Boolean 型の関数オブジェクトを作成
    fun isK(c: Char): Boolean = c.equals("K")
    // こんな形で関数を引数に与えたりすることができる
    // 高階関数は抽象化できるため、便利ではあるが、使いこなすまでには慣れが必要な気がする
    return first(str, ::isK)
}

fun firstUpperfun(str: String): Int {
    fun isUpper(c: Char): Boolean = c.isUpperCase()
    return first(str, ::isUpper)
}



// ラムダ式: 以下のように関数オブジェクトを直接変数に指定するコードのこと
// = {} で囲う使い方をしている. returnの記載は不要
// 変数で関数オブジェクトを宣言できる
fun ramda() {
    val square: (Int) -> Int = {
        i: Int -> i * i
    }

    // 暗黙の変数itを使用することができる
    val square2: (Int) -> Int = {
        it * it
    }
}

// ラムダ式と高階関数の組み合わせ
fun firstWhiteSpace(str: String): Int {
    val isWhiteSpace: (c: Char) -> Boolean = {
        it.isWhitespace()
    }
    return first(str, isWhiteSpace)
}

fun firstWhiteSpaceNext(str: String): Int =
        first(str, {it.isWhitespace()})

// 構文糖衣: 上記のパターンが多いため、ラムダ式を引数リストの外に出すことができる
fun firstWhiteSpaceRamda(str: String): Int =
        first(str) {it.isWhitespace()}



// クロージャ
fun foo(): Int {
    val a = 1
    val b = 2
    return a + b
}

fun bar(): Int {
    val c = 3
    return a + c
}

fun getCounter(): () -> Int {
    var count = 0
    return {
        // ラムダ式で直接()->Int型を記載
        count++
    }
}
fun mainCloser(args: Array<String>) {
    val counter1 = getCounter()
    val counter2 = getCounter()
    // counterにアクセスするたびに、関数オブジェクトが実行される
    // counterでgetCounter()のcountにアクセスしている
    // つまりgetCounter()のローカル変数にmainCloser()でアクセスできている
    // このようにスコープで変数が扱える関数オブジェクトをクロージャと呼ぶ
    println(counter1) // 0出力
    println(counter1) // 1出力
    println(counter2) // 0出力
    println(counter1) // 2出力
    println(counter2) // 1出力
}


// インライン関数: 引数の関数オブジェクトをコンパイル時にインライン転換される関数のこと
// 要は、引数に関数をもつメソッドに使われる. マクロのようなもの
// 使い方: アノテーションでinlineを付加するだけ
// 高階関数は便利だけどコストが高くなりがち
// ループ処理で高階関数を何回も使用する場合に有用で、コードは大きくなるがパフォーマンスは向上する
fun log(debug: Boolean = true, message: () -> String) {
    if (debug) {
        println(message)
    }

    fun mainInline(args: Array<String>) {
        log { "メッセージ出力される" }
        log(false) { "メッセージ出力されない" }
    }
}

// 上記のような高階関数にインライン関数を用いる
inline fun logInline(debug: Boolean = true, message: () -> String) {
    if (debug) {
        print(message())
    }
}


// 非ローカルリターン: ラムダ式内でreturnによるリターンが可能
// inline展開する必要がある

// まずはインライン関数とforEach
inline fun forEach(str: String, f: (Char) -> Unit) {
    for (c in str) {
        // c: 引数Char
        // f()により型変換を行なっている
        f(c)
    }
}

// 次はforEachと非ローカルリターン
// 文字列内に数字が含まれているか否かをチェックする関数
fun containsDigit(str: String): Boolean {
    // ラムダ式を使用してforをまわす
    forEach(str) {
        // その中で非ローカルリターンを使用してリターンする
        if (it.isDigit()) {
            // 数字が見つかったら非ローカルリターンでただちにreturn
            return true
        }
    }
    return false
}


// ラベルへのリターン: 自身の処理から脱出したい場合に用いる
fun containsDigitLabel(str: String): Boolean {
    var result: Boolean = false
    // ラムダ式直前でラベル指定
    forEach(str) here@ {
        if (it.isDigit()) {
            result = true
            return@here
        }
    }
    return result
}

// 関数を指定してリターンすることもできる. リターン対象が推論できる場合に限る
fun containDigitLabelFun(str: String): Boolean {
    var result = false
    forEach(str) {
        if (it.isDigit()) {
            result = true
            // 関数名をラベル指定
            return@forEach
        }
    }
    return result
}



// 無名関数: ラムダ式のように関数オブジェクトを直接得る方法のもう一つが無名関数
// 通常の関数とほとんど同じだが、名前を持たない特徴がある

// 以下のラムダ式と無名関数をまとめて関数リテラルと呼ぶ
fun fugafuga() {
    // ラムダ式
    val square1: (Int) -> Int = {i: Int ->
        i * i
    }

    // 無名関数
    // returnで返す. ラムダ式と違って非ローカルリターンができない
    val square2: (Int) -> Int = fun(i: Int): Int {
        return i * i
    }

    // 無名関数省略バージョン
    val square3: (Int) -> Int = fun(i: Int) = i * i
}


/**
 * 第7章 オブジェクトからクラスへ
 */

// オブジェクトの生成: object{}で生成できる
fun mainObject(args: Array<String>) {

    // バケツオブジェクト
    // オブジェクト式
    val bucket = object {
        // バケツの容量
        val capacity: Int = 5
        // 水の量
        var quentity: Int = 0

        // バケツを水で満たす
        fun fill() {
            quentity = capacity
        }

        // 排水する
        fun drainAway() {
            quentity = 0
        }

        // 入っている水の量を出力する
        fun printQuentity() {
            println(quentity)
        }
    }

    bucket.printQuentity() //0
    bucket.fill()
    bucket.printQuentity() //5
    bucket.drainAway()
    bucket.printQuentity() //0
}


// インターフェース: バケツオブジェクトに型を与えるためにインターフェースを実装する
// オブジェクトはインターフェース実装できるので、ほとんどクラスと変わらないイメージ
interface Bucket {
    fun fill()
    fun drawAnyway()
    fun pourTo(that: Bucket)

    fun getCapacity(): Int
    fun getQuantity(): Int
    fun setQuautity(quantity: Int)
}

fun mainInterface(args: Array<String>) {
    // Bucketインターフェースを実装する
    val bucket = object: Bucket {
        override fun fill() {}
        override fun drawAnyway() {}
        override fun pourTo(that: Bucket) {}
        override fun getCapacity(): Int {return 5}
        override fun getQuantity(): Int {return 0}
        override fun setQuautity(quantity: Int) {}
    }
}


// プロパティ
interface BucketProperty {
    // プロパティ. 変数のようにデータの持ち方は定義しない
    val capacity: Int
    var quantity: Int
}

fun mainProperty(args: Array<String>) {
    fun createBucket() = object : BucketProperty {
        // 定義したインターフェースのプロパティもoverrideする
        override val capacity: Int = 0
        override var quantity: Int = 0
    }
}


// クラス: クラスはオブジェクトの設計図であると例えられる
// オブジェクトと違い、コンストラクタを定義することができる
// クラスを用いると簡単にオブジェクトを生成することができる
class BucketImpl(_capacity: Int) : BucketProperty {
    override val capacity: Int = _capacity
    override var quantity: Int = 0
}

