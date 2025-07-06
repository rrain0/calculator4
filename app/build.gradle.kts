plugins {
  alias(libs.plugins.android.application)
  alias(libs.plugins.kotlin.android)
  alias(libs.plugins.kotlin.compose)
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
    
    javaCompileOptions {
      annotationProcessorOptions {
        arguments += "room.schemaLocation" to "$projectDir/schemas"
      }
    }
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
  configurations {
    all {
      // Exclude one of duplicate dependencies
      // Maybe i need just clear .jar files cache to avoid error
      exclude(group = "com.android.support", module = "support-compat")
    }
  }
  compileOptions {
    // TODO make java 11
    sourceCompatibility = JavaVersion.VERSION_17
    // TODO make java 11
    targetCompatibility = JavaVersion.VERSION_17
  }
  kotlinOptions {
    // TODO make java 11
    jvmTarget = "17"
  }
  buildFeatures {
    compose = true
    buildConfig = true
    dataBinding = true
    viewBinding = true
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
  
  // lombok - аннотации Getter Setter
  compileOnly("org.projectlombok:lombok:1.18.38")
  annotationProcessor("org.projectlombok:lombok:1.18.38")
  testCompileOnly("org.projectlombok:lombok:1.18.38")
  testAnnotationProcessor("org.projectlombok:lombok:1.18.38")
  
  // Похож на css flex
  // https://github.com/google/flexbox-layout
  implementation("com.google.android.flexbox:flexbox:3.0.0")
  
  // LifeCycle LiveData
  implementation("android.arch.lifecycle:extensions:1.1.1")
  annotationProcessor("android.arch.lifecycle:common-java8:1.1.1")
  
  // Lightweight StreamAPI
  implementation("com.annimon:stream:1.2.2")
  
  // Room - доступ к БД
  implementation("androidx.room:room-runtime-android:2.7.2")
  implementation("android.arch.persistence.room:runtime:1.1.1")
  annotationProcessor("android.arch.persistence.room:compiler:1.1.1")
  
  
  // Замена java.time
  implementation("com.jakewharton.threetenabp:threetenabp:1.4.9")
  
  
  
}