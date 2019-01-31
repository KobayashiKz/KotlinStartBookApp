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
