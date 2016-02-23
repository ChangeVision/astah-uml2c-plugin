# Astah UML2C Plug-in

UMLモデリングツールastahのクラス図のモデルから、C言語のスケルトンコードを出力するプラグインです。
GoogleMockのコード出力機能も持ちます。
このプラグインは、（株）永和システムマネジメントの清水靖博氏が開発したものをチェンジビジョンが一部修正して公開しています。

変換ルールなどについては、次のWEBサイトを参照ください。

 - [組込みソフト開発 ノウハウ集](http://y-philly.bitbucket.org/embedded-know-how/index.html)


## 1. インストール方法

プラグインのjarファイルとテンプレートファイル一式を `[ホームフォルダ]/.astah/[astahのエディション名]/plugins` に
コピーしてください。
- テンプレートファイルは、次のパスになるように配置してください。
   - `[ホームフォルダ]/.astah/[astahのエディション名]/plugins/uml2c/header.vm`

## 2. 使い方

※サンプルプロジェクト（uml2c-plugin-sample.asta）ベースで説明します。

### 2.1. シングルインスタンスモジュールのコード生成

クラス図から `Foo` クラスを選択してください。
メニューバーから、[ツール]→[UML2C プラグイン] を選択してください。

* [Cスケルトンコード生成] を選ぶと、Cスケルトンコードが出力されます
* [GoogleMock コード生成] を選ぶと、GoogleMock のコードが出力されます

※コードは、プロジェクトファイルがあるフォルダに出力されます。

### 2.2. マルチインスタンスモジュールのコード生成

クラス図から `Bar` クラスを選択してください。

以下 **2.1.** 同様なので、省略します。

### 2.3. 型による動的インターフェイス（スーパークラス）のコード生成

クラス図から `Abstract` クラスを選択してください。

以下 **2.1.** 同様なので、省略します。


### 2.4. 型による動的インターフェイス（サブクラス）のコード生成

Ver.0.1.2 では非対応です。

## ライセンス
Copyright 2016 ChangeVision,Inc.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

以上
