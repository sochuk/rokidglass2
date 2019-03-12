# Glass UI
## 引入库
### Gradle

## 功能列表
### GlassButton
Glass自定义的Button

`Focused`:  

![](images/glass_button_focused.png)

`Normal`

![](images/glass_button_normal.png)

#### 用法
``` xml
<com.rokid.glass.ui.button.GlassButton
    android:id="@+id/confirm_btn"
    style="@style/GlassButton"
    android:layout_width="@dimen/glass_button_width"
    android:layout_height="@dimen/glass_button_height"
    android:text="@string/confirm_text" />
```
### GlassDialog
提供了一系列常用的对话框,通过不同Builder 来构建不同类型的对话框。
目前提供的Builder:
#### NotificationDialogBuilder
#### SimpleVoiceDialogBuilder
#### ImageDialogBuilder
#### SimpleMessageDialogBuilder
#### SimpleContentDialogBuilder
#### ImageContentDialogBuilder
#### CustomerSimpleMsgDialogBuilder
#### CustomerImageDialogBuilder
#### CustomerImageContentDialogBuilder


### 工具类
#### CountDownManager