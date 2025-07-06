plugins {
  id("com.google.devtools.ksp")
  alias(libs.plugins.android.application)
  alias(libs.plugins.kotlin.android)
  alias(libs.plugins.kotlin.compose)
  id("androidx.room")
}

android {
  namespace = "com.rrain.calculator4"
  compileSdk = 36
  
  defaultConfig {
    applicationId = "com.rrain.calculator4"
    minSdk = 26 // Oreo 8.0 API 26
    targetSdk = 36
    versionCode = 30
    versionName = "1.8.8"
    
    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }
  
  buildTypes {
    release {
      isMinifyEnabled = false
      proguardFiles(
        getDefaultProguardFile("proguard-android-optimize.txt"),
        "proguard-rules.pro"
      )
    }
  }
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
  }
  kotlinOptions {
    jvmTarget = "17"
  }
  buildFeatures {
    compose = true
    buildConfig = true
    dataBinding = true
    viewBinding = true
  }
  room {
    // Specify your desired schema directory here
    schemaDirectory("$projectDir/schemas")
  }
}


dependencies {
  
  implementation(libs.androidx.core.ktx)
  implementation(libs.androidx.lifecycle.runtime.ktx)
  implementation(libs.androidx.activity.compose)
  implementation(platform(libs.androidx.compose.bom))
  implementation(libs.androidx.ui)
  implementation(libs.androidx.ui.graphics)
  implementation(libs.androidx.ui.tooling.preview)
  implementation(libs.androidx.material3)
  testImplementation(libs.junit)
  androidTestImplementation(libs.androidx.junit)
  androidTestImplementation(libs.androidx.espresso.core)
  androidTestImplementation(platform(libs.androidx.compose.bom))
  androidTestImplementation(libs.androidx.ui.test.junit4)
  debugImplementation(libs.androidx.ui.tooling)
  debugImplementation(libs.androidx.ui.test.manifest)
  
  /*

  implementation fileTree(include: ['*.jar'], dir: 'libs')

  testImplementation 'junit:junit:4.13.2'
  androidTestImplementation 'androidx.test.ext:junit:1.2.1'
  androidTestImplementation 'androidx.test.espresso:espresso-core:3.6.1'

*/
  implementation("androidx.appcompat:appcompat:1.7.1")
  
  implementation("com.google.android.material:material:1.12.0")
  implementation("com.google.android.gms:play-services-ads:24.4.0")
  implementation("androidx.gridlayout:gridlayout:1.1.0")
  implementation("androidx.constraintlayout:constraintlayout:2.2.1")
  implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
  implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.9.1")
  
  // Похож на css flex
  // https://github.com/google/flexbox-layout
  implementation("com.google.android.flexbox:flexbox:3.0.0")
  
  // LifeCycle LiveData
  implementation("android.arch.lifecycle:extensions:1.1.1")
  annotationProcessor("android.arch.lifecycle:common-java8:1.1.1")
  
  // Lightweight StreamAPI
  implementation("com.annimon:stream:1.2.2")
  
  // Room - доступ к БД
  val roomV = "2.7.2"
  implementation("androidx.room:room-runtime-android:$roomV")
  annotationProcessor("androidx.room:room-compiler:$roomV")
  ksp("androidx.room:room-compiler:$roomV")
  implementation("androidx.room:room-ktx:$roomV")
  
  
  // Замена java.time
  implementation("com.jakewharton.threetenabp:threetenabp:1.4.9")
}
