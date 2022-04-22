<a id="chapter1"></a>
# 🤖JisBot (JavaDiscordBot) [![Download][badge]][link]

[badge]: https://img.shields.io/badge/-%E3%83%80%E3%82%A6%E3%83%B3%E3%83%AD%E3%83%BC%E3%83%89-4FC08D.svg?logo=&style=plastic
[link]: https://github.com/Karafuru0630XpX/JisBot/releases
This bot was created using the discord4j library.    
I made it for the purpose of **quick startup and quick use**.  
I don't know if there is a feature you are looking for, so if there is, please let me know in some way and I will add it to the idea.

<a id="chapter2"></a>
## 🎈Table of Contents

1. [Summary](#chapter1)
2. [Table of contents](#chapter2)
3. [Demo](#chapter3)
4. [Author](#chapter5)
5. [Assumption](#chapter6)
6. [Install](#chapter7)
7. [Usage](#chapter8)
8. [Function](#chapter)
9. [Note](#chapter9)
10. [License](#chapter10)
11. [Idea](#chapter11)
12. [Issues](#chapter12)
13. [Message](#chapter13)
14. [History](#chapter14)
15. [Code Explanation](#chapter15)


<a id="chapter3"></a>
## 👀Demo

![gif](https://github.com/Karafuru0630XpX/JisBot/blob/master/assets/demo.gif)

<a id="chapter5"></a>
## 🔨Author

* [Karafuru - からふる](https://github.com/Karafuru0630XpX/)
    * xpx0karafuru0630@gmail.com

<a id="chapter6"></a>
## 🍌Assumption

What is needed for execution etc.

* java
* Discord bot (please create from the Developer Portal)

<a id="chapter7"></a>
## 🌐Install

Libraries are compiled in jar files

Please download your favorite version of the jar from Release

<a id="chapter8"></a>
## 💻Usage

1. Install jar
2. Launch command prompt
3. Go to the directory where the jar is located (see below)
```
cd <Directory>
```
4.  Start once with the following command to create configurations, etc.
```
java -jar <FileName>
```
5. Write tokens, etc. in the config, and write api key if necessary.
6. Start with the command introduced in #4.<br><br>
   💡Hint  
   You can easily start it by creating a bat in the same directory as the jar and storing the commands.
```
java -jar <FileName>
```

<a id="chapter"></a>
## 📒Function
Will add when v1.0 is released

###⌘コマンド
###⚙️Config

<a id="chapter9"></a>
## 📝Memo

Fill in the api key and other information in the config if necessary.  
If you do not fill in the api key, commands using Api will not be available (they do not support errors, so you will need to restart the system).

<a id="chapter10"></a>
## 📜License

[GNU General Public License v3.0](https://ja.wikipedia.org/wiki/GNU_General_Public_License).

<a id="chapter11"></a>
## 💭Idea
* Ensure that you are regularly notified of weather forecasts.
* If the Api key is not mentioned, the command will be disabled and will not generate an error.

<a id="chapter12"></a>
## ⚠Issues
None at present  
Let me know if you have any problems

<a id="chapter13"></a>
## 💬Message
For developers
I hope that the code is easy to read so that people can use it if they don't know how to use discord4j.  
We would appreciate it if you could make extensions to the code (please check the license, of course).

<a id="chapter14"></a>
## ⌛History

Create repository - 2022/04/02

<a id="chapter15"></a>
## 👨‍🏫コード解説

v1.0が完成したのち解説します

## 📚What was used
* Mainly used  
  <img src="https://img.shields.io/badge/-Java-007396.svg?logo=java&style=plastic"> JDK 15.0.1<br>
  <img src="https://img.shields.io/badge/-Intellij%20IDEA-000000.svg?logo=intellijidea&style=plastic"> Intellij IDEA Community Edition 2021.3.3<br>
  <img src="https://img.shields.io/badge/-Discord-7289DA.svg?logo=discord&style=plastic"> Discord Canary<br>
  <img src="https://img.shields.io/badge/-Github-181717.svg?logo=github&style=plastic"> GitHub<br>

* Library etc.
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
