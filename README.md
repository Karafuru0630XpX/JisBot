<img src="https://img.shields.io/github/stars/Karafuru0630XpX/JisBot"><img src="https://img.shields.io/github/forks/Karafuru0630XpX/JisBot"><img src="https://img.shields.io/github/license/Karafuru0630XpX/JisBot"><img src="https://img.shields.io/github/issues/Karafuru0630XpX/JisBot"> 

<a id="chapter1"></a>
# 🤖JisBot (JavaDiscordBot) [![Download][badge]][link]

[badge]: https://img.shields.io/badge/-%E3%83%80%E3%82%A6%E3%83%B3%E3%83%AD%E3%83%BC%E3%83%89-4FC08D.svg?logo=&style=plastic
[link]: https://github.com/Karafuru0630XpX/JisBot/releases

discord4jというライブラリを使用して作ったbotです。    
**サクッと起動サクッと利用**を目的に作りました  
求められている機能があるかわかりませんのである場合何らかの方法で伝えていただけるとアイデアに追加します

<a id="chapter2"></a>
## 🎈目次
[test](/play secret)
1. [概要](#chapter1)
2. [目次](#chapter2)
3. [デモ](#chapter3)
4. [作成者](#chapter5)
5. [前提](#chapter6)
6. [インストール](#chapter7)
7. [使い方](#chapter8)
8. [機能](#chapter)
9. [ノート](#chapter9)
10. [ライセンス](#chapter10)
11. [アイデア](#chapter11)
12. [問題](#chapter12)
13. [メッセージ](#chapter13)
14. [歴史](#chapter14)
15. [コード解説](#chapter15)


<a id="chapter3"></a>
## デモ

![gif](https://github.com/Karafuru0630XpX/JisBot/blob/master/assets/demo.gif)

<a id="chapter5"></a>
## 🔨作成者

* [Karafuru - からふる](https://github.com/Karafuru0630XpX/)
* xpx0karafuru0630@gmail.com

<a id="chapter6"></a>
## 前提

実行に必要なものなど

* java
* Discordのbot(デベロッパーポータルから作成してください)

<a id="chapter7"></a>
## インストール

ライブラリはjarファイルにまとめています

jarはReleaseからお好きなバージョンをダウンロードしてください

<a id="chapter8"></a>
## 💻使い方

1. jarをインストール
2. コマンドプロンプトを起動
3. jarのあるディレクトリに移動(下記参照)
```
cd ディレクトリ
```
4. 下記のコマンドで一度起動してコンフィグなどを作成する
```
java -jar ファイル名
```
5. コンフィグにトークンなどを書き込み、必要ならapi keyを書き込んだりする
6. 4のコマンドで起動する<br><br>
💡ヒント  
jarと同じディレクトリにbatを作りコマンドを保存しておくと簡単に起動できます
```
java -jar ファイル名
```

<a id="chapter"></a>
## 機能
v1.0がリリースされたら追加します
###コマンド
###コンフィグ

<a id="chapter9"></a>
## ノート

configのapi keyなどは必要なら記入してください  
記入しない場合は一部コマンドが機能しません

<a id="chapter10"></a>
## ライセンス

[GNU General Public License v3.0](https://ja.wikipedia.org/wiki/GNU_General_Public_License).

<a id="chapter11"></a>
## 💭アイデア
* 定期的に天気予報をお知らせするようにする

<a id="chapter12"></a>
## 問題

現在特になし  
問題があれば教えてください


<a id="chapter13"></a>
## 💬メッセージ
開発者の方へ
discord4jの使い方が分からない場合などにコードを使用してもらえるような見やすいコードになったかと思います。  
拡張機能などを作ってもらえると助かります(もちろんライセンスは確認してください)

<a id="chapter14"></a>
## 歴史

Create repository - 2022/04/02

<a id="chapter15"></a>
## コード解説

v1.0が完成したのち解説します

## 使用したもの
* 主に使用したもの  
<img src="https://img.shields.io/badge/-Java-007396.svg?logo=java&style=plastic"> JDK 15.0.1<br>
<img src="https://img.shields.io/badge/-Intellij%20IDEA-000000.svg?logo=intellijidea&style=plastic"> Intellij IDEA Community Edition 2021.3.3<br>
<img src="https://img.shields.io/badge/-Discord-7289DA.svg?logo=discord&style=plastic"> Discord Canary<br>
<img src="https://img.shields.io/badge/-Github-181717.svg?logo=github&style=plastic"> GitHub<br>
　　
* ライブラリなど  
#### Discord4j 
com.discord4j:discord4j-core  v3.2.2  
#### What3Words
com.what3words:w3w-java-wrapper v3.1.12  
#### cron4j
it.sauronsoftware.cron4j:cron4j v2.2.5  
#### junit
junit:junit v4.13.2  
#### maven-assembly-plugin
org.apache.maven.plugins:maven-assembly-plugin v3.3.0