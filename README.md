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
通知栏通知（出现固定时间后消失）

![](images/notification.png)
``` java
GlassDialog notificationDialog = new GlassDialog.NotificationDialogBuilder(this)
                        .setTitle(getString(R.string.notification_title))
                        .setMessage(getString(R.string.notification_message))
                        .setIconRes(R.mipmap.ic_launcher)
                        .setDuration(3000)
                        .create();
notificationDialog.show();
```
|方法|含义|默认值
|---|---|---|
|setTitle|设置通知栏标题|null|
|setMessage|设置通知栏内容|null|
|setIconRes|设置通知icon||
|setDuration|设置通知栏消息时间(ms)|3000|

#### SimpleVoiceDialogBuilder
纯语音通知
![](images/notify_simple_voice.png)
具体代码参考Sample
```
GlassDialog simpleVoiceDialogBuilder = new GlassDialog.SimpleVoiceDialogBuilder(this)
                        .setTitle(getString(R.string.voice_test))
                        .setConfirmText(getString(R.string.voice_play))
                        .setCancelText(getString(R.string.voice_collapse))
                        .setConfirmListener(new GlassDialogListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(MainActivity.this,
                                        "Click Confirm", Toast.LENGTH_SHORT).show();

                                mSimpleVoiceDialogBuilder.dynamicTitle(getString(R.string.voice_playing));
                                mSimpleVoiceDialogBuilder.dynamicCustomConfirmView(mCustomTimerView);

                                countDownManager.start();
                            }
                        })
                        .setCancelListener(new GlassDialogListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(MainActivity.this,
                                        "Click Cancel", Toast.LENGTH_SHORT).show();

                                if (null != countDownManager) {
                                    countDownManager.cancel();
                                }
                            }
                        });

simpleVoiceDialogBuilder.show();
```
`setTitle`: 设置标题 


#### ImageDialogBuilder
#### SimpleMessageDialogBuilder
#### SimpleContentDialogBuilder
#### ImageContentDialogBuilder
#### CustomerSimpleMsgDialogBuilder
#### CustomerImageDialogBuilder
#### CustomerImageContentDialogBuilder


### 工具类
#### CountDownManager