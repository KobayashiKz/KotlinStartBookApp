# KotlinStartBookApp
Kotlinスタートブックメモ

■■■概要
・JetBrains社開発言語
・2016年に1.0リリース
・JVM言語でJavaが動作する環境でKotlin動かせる

■■■特徴
1. 簡潔
・セミコロンだったりif-else、拡張関数などシンプルに書ける
2. 安全
・nullの扱いが厳格のため安全性が高い
3. JVM言語
・JetBrains社は「KotlinとJavaの相互運用は100%」と言っている
4. 静的型付け
5. オブジェクト志向
・プリミティブ型int,charなどがJavaと異なり、Kotlinには存在しない
・プロパティやオブジェクト宣言などJavaにない機能があったりする
6. 関数型プログラミング


■ ライブデータのライブラリ
1. Kotter Knife
  ・サードパーティー製ライブラリ
2. Kotlin Android Extensions
  ・IDを委譲プロパティに記述していく. この作業を解消してくれる
  ・敷居は低い
3. Data Binding
  ・Google公式
  ・レイアウトとコード間のデータをバインディングしてくれる
4. Anko
  ・JetBrains製
  ・XMLでなくコード(Kotlin DSL)でレイアウトを構築していく
  ・AnkoDSLプレビューでレイアウトを見れる
