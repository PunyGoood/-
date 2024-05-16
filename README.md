# 接口设计文档 


## 接口定义

## 主结构概述

### 1.1 成员变量

`MainActivity` 是一个 Android `Activity`，提供了用户与应用进行交互的主要界面。它包含多个功能按钮，允许用户访问不同的功能模块，即历史记录、设置、语音识别、键盘输入和图片翻译。

## 布局组件

### `beforeLay: LinearLayout`
- 功能: 翻译之前的布局,包含输入框、源语言和目标语言选择等。
- 引用位置: `findViewById(R.id.beforeLay)`
- 属性:
  - `orientation`: `LinearLayout.VERTICAL` - 垂直布局排列。
  - `visibility`: `View.VISIBLE` - 默认显示。

### `afterLay: LinearLayout` 
- 功能: 翻译之后的布局,包含翻译结果和操作按钮等。
- 引用位置: `findViewById(R.id.afterLay)`
- 属性: 
  - `orientation`: `LinearLayout.VERTICAL` - 垂直布局排列。
  - `visibility`: `View.GONE` - 默认隐藏。

### 视图组件

### `tvFrom: TextView`
- 功能: 显示翻译的源语言。
- 引用位置: `findViewById(R.id.tv_from)`
- 属性:
  - `text`: `"Source Language"` - 默认文本。
  - `textSize`: `16sp` - 字体大小。
  - `gravity`: `Gravity.CENTER` - 居中对齐。

### `tvTo: TextView`
- 功能: 显示翻译的目标语言。
- 引用位置: `findViewById(R.id.tv_to)`
- 属性:
  - `text`: `"Target Language"` - 默认文本。
  - `textSize`: `16sp` - 字体大小。
  - `gravity`: `Gravity.CENTER` - 居中对齐。

### `edContent: EditText`
- 功能: 用户输入要翻译的内容。
- 引用位置: `findViewById(R.id.edContent)`
- 属性:
  - `hint`: `"Enter text to translate"` - 提示文本。
  - `inputType`: `InputType.TYPE_CLASS_TEXT` - 文本输入类型。
  - `imeOptions`: `EditorInfo.IME_ACTION_DONE` - 输入法动作为完成。

### `ivClearTx: ImageView`
- 功能: 清空输入框的内容。
- 引用位置: `findViewById(R.id.ivClearTx)`
- 属性:
  - `src`: `@drawable/ic_clear` - 清除图标。
  - `contentDescription`: `"Clear input text"` - 内容描述。
  - `padding`: `16dp` - 按钮内边距。

### `tvTranslation: TextView`
- 功能: 执行翻译操作。
- 引用位置: `findViewById(R.id.tvTranslation)`
- 属性:
  - `text`: `"Translate"` - 按钮文本。
  - `textSize`: `16sp` - 字体大小。
  - `gravity`: `Gravity.CENTER` - 居中对齐。
  - `padding`: `16dp` - 按钮内边距。

### `tvResult: TextView`
- 功能: 显示翻译结果。
- 引用位置: `findViewById(R.id.tvResult)`
- 属性:
  - `text`: `"Translated Text"` - 默认文本。
  - `textSize`: `18sp` - 字体大小。
  - `gravity`: `Gravity.CENTER` - 居中对齐。
  - `padding`: `16dp` - 内边距。

### `ivCopyTx: ImageView`
- 功能: 复制翻译结果到剪贴板。
- 引用位置: `findViewById(R.id.ivCopyTx)`
- 属性:
  - `src`: `@drawable/ic_copy` - 复制图标。
  - `contentDescription`: `"Copy translated text"` - 内容描述。
  - `padding`: `16dp` - 按钮内边距。

### 1.2 构造函数 

MainActivity() - 构造 MainActivity 实例，并在 onCreate() 方法中进行初始化。
方法 

### `onCreate(savedInstanceState: Bundle?)`
- 初始化 `MainActivity` 实例
- 设置应用主题
- 加载布局文件 `activity_main.xml`
- 绑定视图组件,如按钮、文本框等
- 设置各个视图组件的点击事件监听器

## 事件处理方法

### `onClick(view: View)`
- 处理用户点击按钮的事件
- 根据点击的按钮 ID 执行不同的操作
  - 点击"翻译"按钮 - 调用 `translateText()` 方法进行文本翻译
  - 点击"复制"按钮 - 调用 `copyTranslatedText()` 方法将翻译结果复制到剪贴板
  - 点击"清空"按钮 - 调用 `clearInputText()` 方法清空输入文本框

## 辅助方法

### `translateImage(imagePath: String)`
- 接收图片路径作为参数
- 调用图片文字翻译 API(如百度翻译 API)进行图片中文字的翻译
- 将翻译结果更新到界面的文本框中

### `translateText()`
- 获取用户输入的文本
- 调用文本翻译 API 进行文本翻译
- 将翻译结果更新到界面的文本框中

### `copyTranslatedText()`
- 将当前翻译结果复制到系统剪贴板

### `clearInputText()`
- 清空用户输入的文本框

## 事件监听器

### 多个按钮的 `setOnClickListener`
- 为界面上的各个按钮设置点击事件监听器
- 当用户点击按钮时,调用对应的事件处理方法

## 权限需求

### `android.permission.INTERNET`
- 允许应用访问网络,以调用在线翻译 API

## 依赖关系

### Android SDK
- 基于 Android 平台开发

### 百度翻译 API
- 调用百度翻译 API 进行文本和图片的翻译



##  文本翻译概述

`KeyboardActivity` 是一个继承自 `AppCompatActivity` 的 Android Activity 类，实现了文本翻译功能。它允许用户选择翻译的源语言和目标语言，并提供了文本输入、翻译结果展示以及复制功能。

### 2.1 成员变量



- `fromLanguage`: `String` - 设置翻译的源语言，默认为 "auto" 以自动检测。
- `toLanguage`: `String` - 设置翻译的目标语言，默认为 "auto" 以自动检测。
- `appId`: `String` - 百度翻译平台分配的应用程序 ID。
- `key`: `String` - 百度翻译平台分配的密钥。

### 组件清单

1. `ivClearTx`: `ImageView` - 清空输入框的图标按钮。
2. `ivCopyTx`: `ImageView` - 复制翻译结果的图标按钮。
3. `tvTranslation`: `TextView` - 触发翻译操作的按钮。
4. `edContent`: `EditText` - 用户输入要翻译文本的编辑框。
5. `tvResult`: `TextView` - 展示翻译结果的文本视图。
6. `spLanguage`: `Spinner` - 选择翻译语言的下拉列表。
7. `resultLay`: `LinearLayout` - 包裹翻译结果的布局。
8. `beforeLay`: `LinearLayout` - 翻译前的布局界面。
9. `afterLay`: `LinearLayout` - 翻译后的布局界面。
10. `tv_from`: `TextView` - 展示源语言的文本视图。
11. `tv_to`: `TextView` - 展示目标语言的文本视图。
12. `myClipboard`: `ClipboardManager` - 用于复制文本到剪贴板。

### 接口设计

1. `ivClearTx`: `ImageView`
   - 功能: 清空输入框 `edContent` 的内容。
   - 事件: 点击清除按钮时, 清空 `edContent` 的文本内容。

2. `ivCopyTx`: `ImageView`
   - 功能: 复制翻译结果 `tvResult` 的内容到剪贴板。
   - 事件: 点击复制按钮时, 使用 `myClipboard` 将 `tvResult` 的文本内容复制到剪贴板。

3. `tvTranslation`: `TextView`
   - 功能: 触发翻译操作, 根据 `spLanguage` 的选择, 将 `edContent` 的文本内容翻译并显示在 `tvResult` 上。
   - 事件: 点击翻译按钮时, 调用翻译服务, 获取翻译结果并更新 `tvResult` 的内容。同时, 更新 `tv_from` 和 `tv_to` 的文本内容以显示源语言和目标语言。

4. `edContent`: `EditText`
   - 功能: 用户输入要翻译的文本内容。

5. `tvResult`: `TextView`
   - 功能: 展示翻译结果。
   - 更新: 在 `tvTranslation` 的事件处理中, 根据翻译服务的结果更新 `tvResult` 的文本内容。

6. `spLanguage`: `Spinner`
   - 功能: 允许用户选择源语言和目标语言。
   - 事件: 当用户选择语言时, 更新 `tv_from` 和 `tv_to` 的文本内容以反映所选的源语言和目标语言。

7. `resultLay`: `LinearLayout`
   - 功能: 包裹翻译结果的布局, 在翻译结果就绪时显示。

8. `beforeLay`: `LinearLayout`
   - 功能: 翻译前的布局界面, 包含 `edContent` 和 `tvTranslation`。

9. `afterLay`: `LinearLayout`
   - 功能: 翻译后的布局界面, 包含 `tvResult`, `ivCopyTx` 和 `resultLay`。

10. `tv_from`: `TextView`
    - 功能: 展示源语言。
    - 更新: 在 `spLanguage` 的事件处理中, 根据所选的源语言更新 `tv_from` 的文本内容。

11. `tv_to`: `TextView`
    - 功能: 展示目标语言。
    - 更新: 在 `spLanguage` 的事件处理中, 根据所选的目标语言更新 `tv_to` 的文本内容。

12. `myClipboard`: `ClipboardManager`
    - 功能: 用于复制文本到剪贴板。
    - 使用: 在 `ivCopyTx` 的事件处理中, 使用 `myClipboard` 将 `tvResult` 的文本内容复制到剪贴板。

### 2.3. 构造函数

- `KeyboardActivity()` - 默认构造函数。
- `KeyboardActivity(parcel: Parcel)` - 通过 `Parcel` 对象反序列化创建 `KeyboardActivity` 实例。


### 2.4 `onCreate` 
>Activity，设置布局，绑定视图，设置监听器等










## 图片翻译概述

### 2.1请求参数



| 参数名      | 类型 | 必填 | 描述                     |
| ----------- | ---- | ---- | ------------------------ |
| `image`     | file | 是   | 要翻译的图像文件         |
| `source_lang` | string | 是   | 图像的原始语言           |
| `target_lang` | string | 是   | 要翻译到的目标语言       |     

### 2.2响应参数

| 参数名           | 类型   | 描述                               |
| ---------------- | ------ | ---------------------------------- |
| `translated_image` | file   | 翻译后的图像文件                  |
| `status`         | string | 请求状态，可能的值为 "success" 或 "error" |
| `message`        | string | 如果请求失败，则包含错误消息        |
