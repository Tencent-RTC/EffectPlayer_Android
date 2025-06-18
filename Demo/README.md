Gift AR provide demos suitable for different development platforms. This article mainly introduces how to quickly run through the Android version of the Gift AR SDK Demo.

## Environmental Requirements
- Android Studio 2.0 or later.

- Android SDK API Level 19 or higher.

- Android 4.4 or higher, mobile devices supporting armeabi-v7a, arm-v8a architecture.


## Import Project

First open Android Studio, select Open an Existing Project, then find the previously downloaded and unzipped Demo project directory, select TCEffectPlayerDemo, click Open, and complete the project import work.


## Modify Dependencies and Configurations

Wait until the import of the project in Android Studio is completed. Then, some modifications need to be made to the Demo project to ensure its subsequent normal operation:
1. Find the app/libs directory, delete the old example SDK AAR file, and replace it with the latest Gift AR SDK AAR file:

   ![](https://write-document-release-1258344699.cos.ap-guangzhou.tencentcos.cn/100026789402/205d7c8c04a311f0aa8e525400bf7822.png)

2. Replace the license information in the project, and replace the value with the license information of Gift AR SDK you have applied for:

   ![](https://write-document-release-1258344699.cos.ap-guangzhou.tencentcos.cn/100026789402/20b6ec2404a311f08c4452540044a08e.png)

3. Modify the application ID of the project, that is, replace the applicationId value in the figure below with the Android package name corresponding to the Gift AR SDK license you have applied for:

   ![](https://write-document-release-1258344699.cos.ap-guangzhou.tencentcos.cn/100026789402/20abe8ff04a311f08bc5525400454e06.png)

4. After the above modification operations are completed, it is recommended to click Build/Clean Project to clean up the cache files of the old AAR in the previous project.


## Animation Resource Configuration

> **Note:**
>

> If you don't need to play your own animation files, you can skip this step.
>


Modify the animation file to be played. You can add the animation file for playback experience in app/main/assets. Then open the MainActivity.java file and modify the FILE_NAME_TEP (large animation) or FILE_NAME_TEPGS (small avatar animation) property at the top. The Demo project will automatically complete the copying of the animation file and other work. Subsequently, you can directly run the Demo project to experience animation playback.


> **Note:**
>
> - The animation files built-in the Demo are not bound to a License. Therefore, you can use your own License for playback experience.
> - If you want to replace your own animation, note that the License information of the animation should be consistent with the License information set within the project. Otherwise, playback will fail due to mismatched License information.


## Compile and Run
1. Ensure that the testing device has already connected normally to Android Studio.

   ![](https://write-document-release-1258344699.cos.ap-guangzhou.tencentcos.cn/100026789402/20930d3104a311f08016525400e889b2.png)

2. Click Run to deploy the test Demo on a real device for experience.

3. After the Demo project is run on a real device, the effect is as follows:

   ![](https://write-document-release-1258344699.cos.ap-guangzhou.tencentcos.cn/100026789402/2061c2ca04a311f0aa645254007c27c5.png)


At this point, the console will first print:

   ``` plaintext
   TCMediaX license result: errCode: 0, msg: Success
   ```

This log information indicates that the SDK license authentication has already succeeded at this point.


Next, you can click the MP4 or TCMP4 button at the bottom to start the playback experience animation.


> **Note:**
>

> Gift AR SDK requires network when performing license authentication for the first time. Therefore, the network is required when running the Demo at startup.
> 


