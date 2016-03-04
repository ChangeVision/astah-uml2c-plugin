# Astah UML2C Plug-in

UMLモデリングツールastahのクラス図のモデルから、C言語のスケルトンコードを出力するプラグインです。
GoogleMockのコード出力機能も持ちます。
このプラグインは、（株）永和システムマネジメントの清水靖博氏が開発したものをチェンジビジョンが一部修正して公開しています。

変換ルールなどについては、次のWEBサイトを参照ください。

 - [組込みソフトウェア開発 ノウハウ集](http://y-philly.bitbucket.org/embedded-know-how/index.html)


## 1. インストール方法

プラグインのjarファイルとテンプレートファイル一式を `[ホームフォルダ]/.astah/[astahのエディション名]/plugins` に
コピーしてください。
- テンプレートファイルは、次のパスになるように配置してください。
   - `[ホームフォルダ]/.astah/[astahのエディション名]/plugins/uml2c/header.vm`
      - Windowsの場合
        - `%USERPROFILE%\.astah\[astahのエディション名]\plugins\uml2c\header.vm`
      - Mac,Linuxの場合
        - `$HOME/.astah/[astahのエディション名]/plugins/uml2c/header.vm`

## 2. 使い方

1. クラス図で、出力対象のクラスを選択します。
1. メニューバーから、次のメニューを選択します
 * Cスケルトンコードを出力する場合は、[ツール]-[UML2C プラグイン]-[Cスケルトンコード生成] を選択してください。
 * GoogleMockコードを出力する場合は、[ツール]-[UML2C プラグイン]-[GoogleMock コード生成] を選択してください。
1. コードの出力先フォルダを選択します。
1. コードが出力されます。
 * コードは、指定したフォルダか、プロジェクトファイルがあるフォルダに出力されます。

※ 以下、サンプルプロジェクト（uml2c-plugin-sample.asta）をベースに説明します。

### 2.1. シングルインスタンスモジュールのコード生成
ステレオタイプ`<<SingleInstance>>`がついたクラスを、シングルインスタンスモジュールとしてコード生成します。
サンプルプロジェクト（uml2c-plugin-sample.asta）では、クラス図から `Foo` クラスを選択してください。

### 2.2. マルチインスタンスモジュールのコード生成
通常のクラスを、マルチインスタンスモジュールとしてコード生成します。
サンプルプロジェクト（uml2c-plugin-sample.asta）では、クラス図から `Bar` クラスを選択してください。

### 2.3. 型による動的インターフェイス（スーパークラス）のコード生成
Abstractなクラスを、型による動的インターフェイスとしてコード生成します。
サンプルプロジェクト（uml2c-plugin-sample.asta）では、クラス図から `Abstract` クラスを選択してください。

### 2.4. 型による動的インターフェイス（サブクラス）のコード生成
未対応です。
スーパークラスをもつクラスを、型による動的インターフェイスとしてコード生成します。
サンプルプロジェクト（uml2c-plugin-sample.asta）では、クラス図から`Concrete` クラスを選択してください。

## 3. テンプレートファイルについて
- テンプレートファイルの文字コードは、UTF-8 としてください。

## 4. オプション

本プラグインは、 コードフォーマッタ [Uncrustify](http://uncrustify.sourceforge.net/http://uncrustify.sourceforge.net/)
と連携して、生成コードを任意（プロジェクトごとのコーディングルールなど）の書式に整形することができます。

一度以下の手順を行えば、その後は生成コードに対する整形が自動で行われます。

1. Uncrustify のコンフィグファイルを作成し、 `custom.cfg` という名前で保存します。
1. フォルダ `[ホームフォルダ]/.astah/[astahのエディション名]/plugins/uml2c/uncrustify` を作成します。
1. 上記フォルダに Uncrustify の実行ファイルと、`custom.cfg` を保存します。

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
