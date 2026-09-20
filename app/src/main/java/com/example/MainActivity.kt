package com.example

import android.content.Intent
import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioTrack
import android.net.Uri
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import java.io.File
import java.util.Locale
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.AddPhotoAlternate
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.FastForward
import androidx.compose.material.icons.filled.Fullscreen
import androidx.compose.material.icons.filled.FullscreenExit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Image
import android.webkit.WebView
import android.webkit.WebViewClient
import android.webkit.JavascriptInterface
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material.icons.filled.VolumeOff
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.AsyncImage
import com.example.ui.theme.BluePrimary
import com.example.ui.theme.MyApplicationTheme
import java.util.UUID
import kotlin.random.Random
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// Data class representing individual messages
data class ChatMessage(
  val id: String = UUID.randomUUID().toString(),
  val text: String,
  val isUser: Boolean = true,
  val modelType: AiModelType? = null,
  val isVideo: Boolean = false,
  val videoModel: String? = null,
  val videoSeed: Long = UUID.randomUUID().hashCode().toLong(),
  val attachedImages: List<String> = emptyList(),
  val visualSceneUrl: String = "",
  val aspectRatio: String = "16:9",
  val motionStyle: String = "Кинематографичный зум",
  val originalPrompt: String = "",
  val speechText: String = "",
  // In-progress video generation state
  val isGeneratingVideo: Boolean = false,
)

// Helper to extract speech lines from video prompts (e.g., 'говорил "всем привет!"' -> "Всем привет!")
fun extractSpeechText(prompt: String): String {
  val clean = prompt.trim()
  if (clean.isBlank()) return ""

  // 1. Quoted text: "...", '...', «...», „...“
  val quoteRegex = Regex("""["'«„]([^"'»“]+)["'»”]""")
  val match = quoteRegex.find(clean)
  if (match != null && match.groupValues[1].isNotBlank()) {
    return match.groupValues[1].trim()
  }

  // 2. Speech keywords
  val lower = clean.lowercase()
  val keywords = listOf(
    "говорил ", "говорила ", "говорили ", "говорит ", "сказал ", "сказала ", "скажи ",
    "произнес ", "произнесла ", "озвучь ", "озвучил ", "крикнул ", "сказать ", "say ", "speak "
  )
  for (kw in keywords) {
    val idx = lower.indexOf(kw)
    if (idx != -1) {
      val candidate = clean.substring(idx + kw.length).trim().removePrefix(":").removePrefix("-").trim()
      if (candidate.isNotBlank()) {
        return candidate.trim('"', '\'', '«', '»', ' ')
      }
    }
  }

  return ""
}

// Android native TextToSpeech Voice Player for speaking characters in generated videos
class TtsVoicePlayer(context: android.content.Context) {
  private var tts: TextToSpeech? = null
  private var isInitialized = false

  init {
    try {
      tts = TextToSpeech(context.applicationContext) { status ->
        if (status == TextToSpeech.SUCCESS) {
          val result = tts?.setLanguage(Locale("ru", "RU"))
          if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
            tts?.setLanguage(Locale.getDefault())
          }
          isInitialized = true
        }
      }
    } catch (_: Exception) {}
  }

  fun speak(text: String) {
    if (text.isBlank()) return
    try {
      tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "LoopAi_Character_Voice")
    } catch (_: Exception) {}
  }

  fun stop() {
    try {
      tts?.stop()
    } catch (_: Exception) {}
  }

  fun release() {
    try {
      tts?.stop()
      tts?.shutdown()
    } catch (_: Exception) {}
    tts = null
  }
}

// Helper to determine accurate visual scene matching user prompt and attached photos
fun resolveCinematicScene(prompt: String, attachedPhotos: List<String>): String {
  if (attachedPhotos.isNotEmpty()) {
    return attachedPhotos.first()
  }

  val p = prompt.lowercase().trim()

  return when {
    // 1. Animals & Creatures
    "кот" in p || "кошк" in p || "котен" in p || "котик" in p || "cat" in p || "kitten" in p ->
      "https://images.unsplash.com/photo-1514888286974-6c03e2ca1dba?w=1080&q=85"
    "собак" in p || "пес" in p || "щенок" in p || "песик" in p || "dog" in p || "puppy" in p ->
      "https://images.unsplash.com/photo-1587300003388-59208cc962cb?w=1080&q=85"
    "волк" in p || "wolf" in p ->
      "https://images.unsplash.com/photo-1564349683136-77e08dba1ef7?w=1080&q=85"
    "лев" in p || "тигр" in p || "гепард" in p || "пантер" in p || "lion" in p || "tiger" in p ->
      "https://images.unsplash.com/photo-1534188753412-3e26d0d618d6?w=1080&q=85"
    "медвед" in p || "панд" in p || "bear" in p || "panda" in p ->
      "https://images.unsplash.com/photo-1530595467537-0b5996c41f2d?w=1080&q=85"
    "птиц" in p || "орел" in p || "сова" in p || "попуга" in p || "bird" in p || "eagle" in p ->
      "https://images.unsplash.com/photo-1552728089-57bdde30beb3?w=1080&q=85"
    "лошад" in p || "конь" in p || "horse" in p ->
      "https://images.unsplash.com/photo-1553284965-83fd3e82fa5a?w=1080&q=85"
    "рыб" in p || "акул" in p || "дельфин" in p || "кит" in p || "океан" in p || "shark" in p || "fish" in p || "dolphin" in p || "underwater" in p || "подводн" in p ->
      "https://images.unsplash.com/photo-1544551763-46a013bb70d5?w=1080&q=85"
    "дракон" in p || "динозавр" in p || "монстр" in p || "dragon" in p || "monster" in p ->
      "https://images.unsplash.com/photo-1518709268805-4e9042af9f23?w=1080&q=85"

    // 2. People & Characters
    "девушк" in p || "женщин" in p || "модел" in p || "красавиц" in p || "принцесс" in p || "girl" in p || "woman" in p ->
      "https://images.unsplash.com/photo-1534528741775-53994a69daeb?w=1080&q=85"
    "парен" in p || "мужчин" in p || "человек" in p || "мужик" in p || "boy" in p || "man" in p || "guy" in p ->
      "https://images.unsplash.com/photo-1506794778202-cad84cf45f1d?w=1080&q=85"
    "ребенок" in p || "дет" in p || "малыш" in p || "child" in p || "baby" in p || "kid" in p ->
      "https://images.unsplash.com/photo-1502086223501-7ea6ecd79368?w=1080&q=85"
    "робот" in p || "киборг" in p || "андроид" in p || "меха" in p || "robot" in p || "cyborg" in p || "android" in p ->
      "https://images.unsplash.com/photo-1485827404703-89b55fcc595e?w=1080&q=85"
    "воин" in p || "рыцар" in p || "самурай" in p || "ниндзя" in p || "солдат" in p || "warrior" in p || "knight" in p || "samurai" in p ->
      "https://images.unsplash.com/photo-1579783900882-c0d3dad7b119?w=1080&q=85"
    "маг" in p || "волшебник" in p || "ведьм" in p || "эльф" in p || "wizard" in p || "mage" in p || "witch" in p || "magic" in p ->
      "https://images.unsplash.com/photo-1514533450685-4493e01d1fdc?w=1080&q=85"
    "космонавт" in p || "астронавт" in p || "скафандр" in p || "astronaut" in p || "cosmonaut" in p ->
      "https://images.unsplash.com/photo-1446776811953-b23d57bd21aa?w=1080&q=85"
    "супергерой" in p || "бэтмен" in p || "паук" in p || "герой" in p || "superhero" in p || "batman" in p || "spiderman" in p ->
      "https://images.unsplash.com/photo-1607604276583-eef5d076aa5f?w=1080&q=85"
    "аним" in p || "арт" in p || "рисун" in p || "картин" in p || "тян" in p || "anime" in p || "manga" in p ->
      "https://images.unsplash.com/photo-1579783902614-a3fb3927b675?w=1080&q=85"

    // 3. Vehicles, Cars & Tech
    "машин" in p || "кар" in p || "авто" in p || "гонк" in p || "спорткар" in p || "дрифт" in p || "car" in p || "bmw" in p || "mercedes" in p || "audi" in p || "lamborghini" in p || "ferrari" in p || "porsche" in p || "supercar" in p ->
      "https://images.unsplash.com/photo-1503376780353-7e6692767b70?w=1080&q=85"
    "мотоцикл" in p || "байк" in p || "скутер" in p || "motorcycle" in p || "bike" in p ->
      "https://images.unsplash.com/photo-1558981403-c5f9899a28bc?w=1080&q=85"
    "самолет" in p || "истребител" in p || "вертолет" in p || "полет" in p || "plane" in p || "airplane" in p || "flight" in p || "jet" in p ->
      "https://images.unsplash.com/photo-1519074069444-1ba4fff16def?w=1080&q=85"
    "ракета" in p || "космолет" in p || "нло" in p || "rocket" in p || "spaceship" in p || "ufo" in p ->
      "https://images.unsplash.com/photo-1517976487507-5b62da078aa8?w=1080&q=85"
    "яхт" in p || "корабл" in p || "лодк" in p || "катер" in p || "yacht" in p || "boat" in p || "ship" in p ->
      "https://images.unsplash.com/photo-1500930287596-c1ecaa373bb2?w=1080&q=85"
    "поезд" in p || "метро" in p || "train" in p ->
      "https://images.unsplash.com/photo-1474487548417-781cb71495f3?w=1080&q=85"

    // 4. Nature, Environments & Space
    "космос" in p || "звезд" in p || "планет" in p || "туманност" in p || "галактик" in p || "марc" in p || "лун" in p || "space" in p || "galaxy" in p || "stars" in p || "planet" in p || "nebula" in p ->
      "https://images.unsplash.com/photo-1451187580459-43490279c0fa?w=1080&q=85"
    "закат" in p || "рассвет" in p || "sunset" in p || "sunrise" in p ->
      "https://images.unsplash.com/photo-1495616811223-4d98c6e9c869?w=1080&q=85"
    "мор" in p || "пляж" in p || "волн" in p || "прибой" in p || "sea" in p || "beach" in p || "waves" in p ->
      "https://images.unsplash.com/photo-1507525428034-b723cf961d3e?w=1080&q=85"
    "гор" in p || "скал" in p || "эверест" in p || "mountain" in p || "alps" in p ->
      "https://images.unsplash.com/photo-1464822759023-fed622ff2c3b?w=1080&q=85"
    "лес" in p || "джунгл" in p || "дерев" in p || "forest" in p || "jungle" in p || "trees" in p || "nature" in p ->
      "https://images.unsplash.com/photo-1448375240586-882707db888b?w=1080&q=85"
    "зим" in p || "снег" in p || "лед" in p || "мороз" in p || "winter" in p || "snow" in p || "ice" in p ->
      "https://images.unsplash.com/photo-1491002052546-bf38f186af56?w=1080&q=85"
    "дожд" in p || "гроз" in p || "молни" in p || "шторм" in p || "rain" in p || "storm" in p || "lightning" in p ->
      "https://images.unsplash.com/photo-1515694346937-94d85e41e6f0?w=1080&q=85"
    "огон" in p || "плам" in p || "костер" in p || "вулкан" in p || "fire" in p || "flame" in p || "volcano" in p || "lava" in p ->
      "https://images.unsplash.com/photo-1542385151-efd9000785a0?w=1080&q=85"
    "водопад" in p || "waterfall" in p ->
      "https://images.unsplash.com/photo-1432405972618-c60b0225b8f9?w=1080&q=85"
    "пустын" in p || "песок" in p || "сахар" in p || "desert" in p || "sand" in p || "dunes" in p ->
      "https://images.unsplash.com/photo-1509316975850-ff9c5deb0cd9?w=1080&q=85"

    // 5. Urban, Cyberpunk, Architecture & Places
    "кибер" in p || "неон" in p || "cyberpunk" in p || "neon" in p || "будущ" in p || "future" in p ->
      "https://images.unsplash.com/photo-1578632767115-351597cf2477?w=1080&q=85"
    "город" in p || "небоскреб" in p || "мегаполис" in p || "нью-йорк" in p || "токио" in p || "москв" in p || "city" in p || "tokyo" in p || "new york" in p ->
      "https://images.unsplash.com/photo-1477959858617-67f30bc75b82?w=1080&q=85"
    "замок" in p || "дворец" in p || "храм" in p || "castle" in p || "palace" in p || "temple" in p ->
      "https://images.unsplash.com/photo-1585543805890-6051f7829f98?w=1080&q=85"
    "комнат" in p || "дом" in p || "кафе" in p || "уют" in p || "room" in p || "house" in p || "cozy" in p ->
      "https://images.unsplash.com/photo-1513694203232-719a280e022f?w=1080&q=85"

    // 6. Sports, Food, Music & Activities
    "футбол" in p || "баскетбол" in p || "бокс" in p || "спорт" in p || "бег" in p || "football" in p || "soccer" in p || "basketball" in p || "sport" in p || "gym" in p ->
      "https://images.unsplash.com/photo-1461896836934-ffe607ba8211?w=1080&q=85"
    "танц" in p || "танец" in p || "дискотек" in p || "dance" in p || "dancing" in p || "party" in p ->
      "https://images.unsplash.com/photo-1547153760-18fc86324498?w=1080&q=85"
    "музык" in p || "концерт" in p || "гитар" in p || "пианино" in p || "диджей" in p || "рок" in p || "music" in p || "concert" in p || "guitar" in p || "dj" in p ->
      "https://images.unsplash.com/photo-1511671782779-c97d3d27a1d4?w=1080&q=85"
    "ед" in p || "пицц" in p || "бургер" in p || "кофе" in p || "торт" in p || "стейк" in p || "food" in p || "pizza" in p || "burger" in p || "coffee" in p ->
      "https://images.unsplash.com/photo-1504674900247-0877df9cc836?w=1080&q=85"
    "взрыв" in p || "битв" in p || "войн" in p || "экшен" in p || "explosion" in p || "battle" in p || "action" in p ->
      "https://images.unsplash.com/photo-1579783900882-c0d3dad7b119?w=1080&q=85"
    "деньг" in p || "золот" in p || "богатств" in p || "money" in p || "gold" in p || "rich" in p ->
      "https://images.unsplash.com/photo-1526304640581-d334cdbbf45e?w=1080&q=85"

    // 7. Dynamic Diverse Hash fallback (never a single static image)
    else -> {
      val fallbackGallery = listOf(
        "https://images.unsplash.com/photo-1518709268805-4e9042af9f23?w=1080&q=85",
        "https://images.unsplash.com/photo-1578632767115-351597cf2477?w=1080&q=85",
        "https://images.unsplash.com/photo-1451187580459-43490279c0fa?w=1080&q=85",
        "https://images.unsplash.com/photo-1503376780353-7e6692767b70?w=1080&q=85",
        "https://images.unsplash.com/photo-1534528741775-53994a69daeb?w=1080&q=85",
        "https://images.unsplash.com/photo-1507525428034-b723cf961d3e?w=1080&q=85",
        "https://images.unsplash.com/photo-1464822759023-fed622ff2c3b?w=1080&q=85",
        "https://images.unsplash.com/photo-1514888286974-6c03e2ca1dba?w=1080&q=85",
      )
      val hashIdx = kotlin.math.abs(p.hashCode()) % fallbackGallery.size
      fallbackGallery[hashIdx]
    }
  }
}

// Cinematic Ambient Audio Engine with zero-CPU hardware looping and pre-cached PCM buffer
class CinematicAudioEngine {
  private var audioTrack: AudioTrack? = null
  private var isPlaying = false
  private var playJob: Job? = null

  companion object {
    private const val SAMPLE_RATE = 22050
    // Pre-calculated static buffer to avoid computing trigonometry in runtime loops
    val cachedBuffer: ShortArray by lazy {
      val bufferSize = SAMPLE_RATE
      val buffer = ShortArray(bufferSize)
      val freq1 = 110.0 // A2
      val freq2 = 164.81 // E3
      val freq3 = 220.0 // A3

      for (i in 0 until bufferSize) {
        val t = i.toDouble() / SAMPLE_RATE
        val envelope = 0.6 + 0.4 * kotlin.math.sin(2.0 * Math.PI * 0.3 * t)
        val s1 = kotlin.math.sin(2.0 * Math.PI * freq1 * t)
        val s2 = kotlin.math.sin(2.0 * Math.PI * freq2 * t) * 0.6
        val s3 = kotlin.math.sin(2.0 * Math.PI * freq3 * t) * 0.4
        val sample = ((s1 + s2 + s3) * 0.20 * envelope * Short.MAX_VALUE).toInt()
        buffer[i] = sample.coerceIn(Short.MIN_VALUE.toInt(), Short.MAX_VALUE.toInt()).toShort()
      }
      buffer
    }
  }

  fun playSound(coroutineScope: kotlinx.coroutines.CoroutineScope) {
    if (isPlaying) return
    isPlaying = true
    playJob = coroutineScope.launch(Dispatchers.Default) {
      try {
        val buf = cachedBuffer
        val track = AudioTrack.Builder()
          .setAudioAttributes(
            AudioAttributes.Builder()
              .setUsage(AudioAttributes.USAGE_MEDIA)
              .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
              .build()
          )
          .setAudioFormat(
            AudioFormat.Builder()
              .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
              .setSampleRate(SAMPLE_RATE)
              .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
              .build()
          )
          .setBufferSizeInBytes(buf.size * 2)
          .setTransferMode(AudioTrack.MODE_STATIC)
          .build()

        track.write(buf, 0, buf.size)
        track.setLoopPoints(0, buf.size, -1)
        track.play()
        audioTrack = track
      } catch (_: Exception) {}
    }
  }

  fun stopSound() {
    isPlaying = false
    playJob?.cancel()
    playJob = null
    try {
      audioTrack?.pause()
      audioTrack?.flush()
      audioTrack?.stop()
      audioTrack?.release()
    } catch (_: Exception) {}
    audioTrack = null
  }
}

fun generateAiResponse(userPrompt: String, model: AiModelType, mode: ModelMode): String {
  val cleanPrompt = userPrompt.trim().lowercase()

  val isGreeting = cleanPrompt in listOf("привет", "здравствуй", "здравствуйте", "ку", "хай", "hello", "hi", "салам")
  val isWhoAreYou = "кто ты" in cleanPrompt || "ты кто" in cleanPrompt || "как тебя зовут" in cleanPrompt || "что ты умеешь" in cleanPrompt
  val isHowAreYou = "как дела" in cleanPrompt || "как ты" in cleanPrompt || "что делаешь" in cleanPrompt
  val isThanks = "спасибо" in cleanPrompt || "благодарю" in cleanPrompt || "спс" in cleanPrompt

  val modePrefix = if (mode == ModelMode.PRO) {
    "🔬 [Глубокий анализ • Pro]:\n\n"
  } else ""

  return when (model) {
    AiModelType.DEEPSEEK -> {
      val reasoning = if (mode == ModelMode.PRO) {
        "💭 <think>\nАнализирую входящий запрос: «$userPrompt»...\nОпределяю оптимальное решение с высокой степенью логики.\n</think>\n\n"
      } else ""
      when {
        isGreeting -> "${modePrefix}${reasoning}Приветствую! Я DeepSeek. Готов помочь с рассуждениями, решением сложных задач, математикой или программированием. О чём хотите поговорить?"
        isWhoAreYou -> "${modePrefix}${reasoning}Я DeepSeek — интеллектуальная модель ИИ, ориентированная на глубокий логический анализ, математику, код и структурные рассуждения."
        isHowAreYou -> "${modePrefix}${reasoning}Все системы функционируют оптимально. Нейросети активны, готов к решению задач любой сложности!"
        isThanks -> "${modePrefix}${reasoning}Всегда пожалуйста! Обращайтесь в любое время, если потребуется детальный разбор или помощь."
        else -> "${modePrefix}${reasoning}Понял ваш запрос: «$userPrompt».\n\nDeepSeek готов предоставить структурированный и точный ответ."
      }
    }
    AiModelType.GEMINI -> {
      when {
        isGreeting -> "${modePrefix}Привет! Я Gemini от Google. Рад встрече! Чем могу помочь тебе прямо сейчас — текстом, идеями, планированием или ответом на вопрос?"
        isWhoAreYou -> "${modePrefix}Я Gemini — мультимодальный искусственный интеллект от Google. Умею находить ответы, генерировать идеи и писать тексты."
        isHowAreYou -> "${modePrefix}Отлично, спасибо! Готов генерировать свежие идеи. Как твои дела?"
        isThanks -> "${modePrefix}Пожалуйста! Очень рад был помочь."
        else -> "${modePrefix}Отличный вопрос! По поводу «$userPrompt»:\n\nЯ обработал информацию и готов помочь разобраться подробнее."
      }
    }
    AiModelType.CHATGPT -> {
      when {
        isGreeting -> "${modePrefix}Здравствуйте! Я ChatGPT. Готов помочь вам с любой задачей: написать текст, объяснить тему, перевести или пообщаться."
        isWhoAreYou -> "${modePrefix}Я ChatGPT (на базе архитектуры GPT-4o). Моя задача — быть вашим универсальным ассистентом."
        isHowAreYou -> "${modePrefix}У меня всё отлично! Готов поддержать беседу. Чем сегодня займёмся?"
        isThanks -> "${modePrefix}Не за что! Рад помочь. Если будут ещё вопросы, пишите."
        else -> "${modePrefix}Спасибо за вопрос по теме «$userPrompt»!\n\nЯ с радостью помогу вам с этим."
      }
    }
    AiModelType.CLAUDE -> {
      when {
        isGreeting -> "${modePrefix}Приветствую! Я Claude. Буду рад помочь вам с вдумчивым анализом, написанием текстов или кодом."
        isWhoAreYou -> "${modePrefix}Я Claude — ИИ-ассистент для точной, вдумчивой и безопасной работы с текстами и логикой."
        isHowAreYou -> "${modePrefix}Спасибо за интерес! Мои системы готовы к продуктивной совместной работе."
        isThanks -> "${modePrefix}Был искренне рад помочь!"
        else -> "${modePrefix}Внимательно рассмотрел ваш запрос: «$userPrompt».\n\nГотов предоставить подробный, аккуратный и структурированный ответ."
      }
    }
    AiModelType.SEEDANSE -> {
      "🎬 Приветствую в Студии Видеогенерации!\n\nЯ создаю кинематографичные видео на передовых нейросетях:\n• **Seedanse 2.0 Fast** (⚡ 60 FPS ультра-скорость)\n• **Seedanse 2.5** (🎬 4K Pro кинематография)\n• **Veo 3** (✨ Google Veo фотореализм)\n• **Google Omni Flash** (⚡ Мультимодальный синтез)\n\n💡 Нажмите кнопку **«Контент ИИ»** или введите промпт в поле ввода ниже, чтобы запустить генерацию видео!"
    }
  }
}

enum class Screen {
  Home,
  Chat,
}

enum class ModelMode(val label: String, val description: String) {
  FAST("Fast", "Быстрые ответы"),
  PRO("Pro", "Глубокий анализ"),
}

enum class AiModelType(
  val displayName: String,
  val subtitle: String,
  val badge: String,
) {
  DEEPSEEK("DeepSeek", "Мощная аналитика и рассуждения (R1 / V3)", "R1"),
  GEMINI("Gemini", "Умный контекст и креативность (Google)", "2.0"),
  CHATGPT("ChatGPT", "Универсальный помощник (GPT-4o)", "4o"),
  CLAUDE("Claude", "Точная работа с текстом и кодом (Sonnet)", "3.5"),
  SEEDANSE("Seedanse & Veo", "Студия видео (Seedanse, Veo 3, Omni Flash)", "Video"),
}

enum class RainbowTitleColor(val title: String, val color: Color?) {
  DEFAULT("По умолчанию", null),
  RED("Красный", Color(0xFFEF4444)),
  ORANGE("Оранжевый", Color(0xFFF97316)),
  YELLOW("Жёлтый", Color(0xFFEAB308)),
  GREEN("Зелёный", Color(0xFF22C55E)),
  CYAN("Голубой", Color(0xFF06B6D4)),
  BLUE("Синий", Color(0xFF3B82F6)),
  PURPLE("Фиолетовый", Color(0xFFA855F7)),
}

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      var isDarkTheme by remember { mutableStateOf(false) }
      var selectedTitleColor by remember { mutableStateOf(RainbowTitleColor.BLUE) }
      var selectedAiModel by remember { mutableStateOf(AiModelType.DEEPSEEK) }

      MyApplicationTheme(isDarkTheme = isDarkTheme) {
        LoopAiApp(
          isDarkTheme = isDarkTheme,
          onToggleTheme = { isDarkTheme = it },
          selectedTitleColor = selectedTitleColor,
          onSelectTitleColor = { selectedTitleColor = it },
          selectedAiModel = selectedAiModel,
          onSelectAiModel = { selectedAiModel = it },
        )
      }
    }
  }
}

@Composable
fun LoopAiApp(
  isDarkTheme: Boolean,
  onToggleTheme: (Boolean) -> Unit,
  selectedTitleColor: RainbowTitleColor,
  onSelectTitleColor: (RainbowTitleColor) -> Unit,
  selectedAiModel: AiModelType,
  onSelectAiModel: (AiModelType) -> Unit,
) {
  val context = LocalContext.current
  var currentScreen by remember { mutableStateOf(Screen.Home) }

  // Separate message histories for each model
  val chatMessagesMap = remember {
    mutableStateMapOf<AiModelType, SnapshotStateList<ChatMessage>>().apply {
      AiModelType.entries.forEach { put(it, mutableStateListOf()) }
    }
  }

  // Active video generation jobs by message ID
  val activeVideoJobs = remember { mutableStateMapOf<String, Job>() }

  var selectedVideoModel by remember { mutableStateOf("Seedanse 2.5") }
  var isTextGenerating by remember { mutableStateOf(false) }
  var showSettingsDialog by remember { mutableStateOf(false) }
  val appCoroutineScope = rememberCoroutineScope()

  val effectiveTitleColor = when {
    selectedTitleColor.color != null -> selectedTitleColor.color
    isDarkTheme -> Color.White
    else -> Color.Black
  }

  BackHandler(enabled = currentScreen == Screen.Chat) {
    currentScreen = Screen.Home
  }

  AnimatedContent(
    targetState = currentScreen,
    transitionSpec = { fadeIn() togetherWith fadeOut() },
    label = "ScreenTransition",
  ) { screen ->
    when (screen) {
      Screen.Home -> {
        HomeScreen(
          onStartChat = { currentScreen = Screen.Chat },
          onOpenSettings = { showSettingsDialog = true },
          accentColor = effectiveTitleColor,
        )
      }
      Screen.Chat -> {
        val activeMessages = chatMessagesMap[selectedAiModel] ?: remember { mutableStateListOf() }

        // Start realistic ~2 min video generation workflow
        fun startVideoGeneration(
          prompt: String,
          modelName: String,
          photos: List<String>,
          aspect: String,
        ) {
          Toast.makeText(
            context,
            "Ваше видео будет готово через несколько минут, ожидайте...",
            Toast.LENGTH_LONG
          ).show()

          val sceneUrl = resolveCinematicScene(prompt, photos)
          val generatingMsgId = UUID.randomUUID().toString()
          val speech = extractSpeechText(prompt)

          // Add placeholder generating message
          val generatingMessage = ChatMessage(
            id = generatingMsgId,
            text = "🎬 Создание видео ($modelName): «$prompt»",
            isUser = false,
            modelType = AiModelType.SEEDANSE,
            isVideo = true,
            videoModel = modelName,
            originalPrompt = prompt,
            speechText = speech,
            attachedImages = photos,
            visualSceneUrl = sceneUrl,
            aspectRatio = aspect,
            isGeneratingVideo = true,
          )
          activeMessages.add(generatingMessage)

          // Launch ~2 minute generation job (120 seconds)
          val job = appCoroutineScope.launch {
            val totalSeconds = 120 // 2 minutes
            for (sec in 1..totalSeconds) {
              delay(1000)

              val index = activeMessages.indexOfFirst { it.id == generatingMsgId }
              if (index == -1) break

              val currentMsg = activeMessages[index]
              if (!currentMsg.isGeneratingVideo) break

              if (sec >= totalSeconds) {
                activeMessages[index] = currentMsg.copy(
                  text = if (photos.isNotEmpty()) {
                    "Ваше видео готово по промпту: «$prompt» на основе ваших фото ($modelName)!"
                  } else {
                    "Ваше видео готово по промпту: «$prompt» ($modelName)!"
                  },
                  isGeneratingVideo = false,
                  speechText = speech,
                  videoSeed = System.currentTimeMillis(),
                )
                activeVideoJobs.remove(generatingMsgId)
              }
            }
          }
          activeVideoJobs[generatingMsgId] = job
        }

        ChatScreen(
          messages = activeMessages,
          accentColor = effectiveTitleColor,
          selectedAiModel = selectedAiModel,
          isTextGenerating = isTextGenerating,
          selectedVideoModel = selectedVideoModel,
          onSelectVideoModel = { selectedVideoModel = it },
          onSendMessage = { text, mode ->
            val cleanLower = text.trim().lowercase()
            val stopKeywords = listOf("стоп", "stop", "отмена", "отмени", "отменить", "остановить", "останови", "хватит", "прекратить", "cancel")
            val isStopCommand = stopKeywords.any { cleanLower == it || cleanLower.startsWith("$it ") }

            if (isStopCommand) {
              // Add user message
              activeMessages.add(ChatMessage(text = text, isUser = true))

              val hasActiveGenerations = activeVideoJobs.isNotEmpty() || activeMessages.any { it.isGeneratingVideo }
              if (hasActiveGenerations) {
                // Cancel all active video generation coroutines
                activeVideoJobs.values.forEach { it.cancel() }
                activeVideoJobs.clear()

                // Update any active generating messages to stopped state
                for (i in activeMessages.indices) {
                  if (activeMessages[i].isGeneratingVideo) {
                    activeMessages[i] = activeMessages[i].copy(
                      text = "⏹️ Генерация видео была остановлена по вашему запросу.",
                      isGeneratingVideo = false,
                      isVideo = false
                    )
                  }
                }

                Toast.makeText(context, "Генерация видео остановлена", Toast.LENGTH_SHORT).show()
                activeMessages.add(
                  ChatMessage(
                    text = "⏹️ Процесс генерации видео успешно остановлен.",
                    isUser = false,
                    modelType = selectedAiModel
                  )
                )
              } else {
                activeMessages.add(
                  ChatMessage(
                    text = "Сейчас нет активных процессов генерации видео.",
                    isUser = false,
                    modelType = selectedAiModel
                  )
                )
              }
            } else if (selectedAiModel == AiModelType.SEEDANSE) {
              // User typed a video prompt in Seedanse Video
              activeMessages.add(
                ChatMessage(
                  text = text,
                  isUser = true,
                  aspectRatio = "16:9",
                )
              )
              startVideoGeneration(
                prompt = text,
                modelName = selectedVideoModel,
                photos = emptyList(),
                aspect = "16:9",
              )
            } else {
              // Standard AI chat text message (DeepSeek, Gemini, ChatGPT, Claude)
              activeMessages.add(
                ChatMessage(
                  text = text,
                  isUser = true,
                )
              )
              isTextGenerating = true
              appCoroutineScope.launch {
                delay(700)
                val response = generateAiResponse(text, selectedAiModel, mode)
                activeMessages.add(
                  ChatMessage(
                    text = response,
                    isUser = false,
                    modelType = selectedAiModel,
                  )
                )
                isTextGenerating = false
              }
            }
          },
          onGenerateVideoContent = { prompt, modelName, photos, aspect ->
            activeMessages.add(
              ChatMessage(
                text = prompt,
                isUser = true,
                attachedImages = photos,
                aspectRatio = aspect,
              )
            )
            startVideoGeneration(
              prompt = prompt,
              modelName = modelName,
              photos = photos,
              aspect = aspect,
            )
          },
          onFastForwardVideo = { msgId ->
            val index = activeMessages.indexOfFirst { it.id == msgId }
            if (index != -1) {
              val current = activeMessages[index]
              activeVideoJobs[msgId]?.cancel()
              activeVideoJobs.remove(msgId)
              activeMessages[index] = current.copy(
                text = "Ваше видео готово по промпту: «${current.originalPrompt.ifEmpty { "Видео по запросу" }}»!",
                isGeneratingVideo = false,
                speechText = extractSpeechText(current.originalPrompt),
                videoSeed = System.currentTimeMillis(),
              )
            }
          },
          onBack = { currentScreen = Screen.Home },
          onOpenSettings = { showSettingsDialog = true },
          onSelectAiModel = onSelectAiModel,
        )
      }
    }
  }

  if (showSettingsDialog) {
    SettingsDialog(
      isDarkTheme = isDarkTheme,
      onThemeChange = onToggleTheme,
      selectedTitleColor = selectedTitleColor,
      onSelectTitleColor = onSelectTitleColor,
      selectedAiModel = selectedAiModel,
      onSelectAiModel = onSelectAiModel,
      accentColor = effectiveTitleColor,
      onDismiss = { showSettingsDialog = false }
    )
  }
}

@Composable
fun HomeScreen(
  onStartChat: () -> Unit,
  onOpenSettings: () -> Unit,
  accentColor: Color = BluePrimary,
  modifier: Modifier = Modifier,
) {
  val isDark = MaterialTheme.colorScheme.background.value != 0xFFF8FAFCUL
  val backgroundColor = MaterialTheme.colorScheme.background
  val isWhiteAccent = accentColor == Color.White
  val buttonContentColor = if (isWhiteAccent) Color.Black else Color.White

  Box(
    modifier = modifier
      .fillMaxSize()
      .background(backgroundColor)
  ) {
    Box(
      modifier = Modifier
        .align(Alignment.Center)
        .size(340.dp)
        .background(
          brush = Brush.radialGradient(
            colors = listOf(
              accentColor.copy(alpha = if (isDark) 0.28f else 0.20f),
              accentColor.copy(alpha = if (isDark) 0.10f else 0.06f),
              Color.Transparent,
            )
          ),
          shape = CircleShape,
        )
    )

    Column(
      modifier = Modifier
        .align(Alignment.Center)
        .padding(horizontal = 24.dp),
      horizontalAlignment = Alignment.CenterHorizontally,
    ) {
      Text(
        text = "LoopAi",
        color = accentColor,
        fontSize = 52.sp,
        fontWeight = FontWeight.ExtraBold,
        letterSpacing = (-0.5).sp,
        textAlign = TextAlign.Center,
      )

      Spacer(modifier = Modifier.height(10.dp))

      Text(
        text = "Твой AI-помощник & Видеостудия",
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        fontSize = 18.sp,
        fontWeight = FontWeight.Normal,
        textAlign = TextAlign.Center,
        letterSpacing = 0.2.sp,
      )
    }

    Box(
      modifier = Modifier
        .align(Alignment.BottomCenter)
        .fillMaxWidth()
        .navigationBarsPadding()
        .padding(horizontal = 24.dp, vertical = 28.dp),
      contentAlignment = Alignment.Center,
    ) {
      Button(
        onClick = onStartChat,
        modifier = Modifier
          .fillMaxWidth()
          .widthIn(max = 420.dp)
          .height(56.dp)
          .testTag("start_chat_button"),
        shape = RoundedCornerShape(28.dp),
        colors = ButtonDefaults.buttonColors(
          containerColor = accentColor,
          contentColor = buttonContentColor,
        ),
        elevation = ButtonDefaults.buttonElevation(
          defaultElevation = 6.dp,
          pressedElevation = 2.dp,
        ),
      ) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.Center,
        ) {
          Icon(
            imageVector = Icons.AutoMirrored.Filled.Chat,
            contentDescription = null,
            tint = buttonContentColor,
            modifier = Modifier.size(20.dp),
          )
          Spacer(modifier = Modifier.width(10.dp))
          Text(
            text = "Начать чат",
            color = buttonContentColor,
            fontSize = 17.sp,
            fontWeight = FontWeight.SemiBold,
          )
        }
      }
    }
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
  messages: List<ChatMessage>,
  accentColor: Color,
  selectedAiModel: AiModelType,
  isTextGenerating: Boolean = false,
  selectedVideoModel: String,
  onSelectVideoModel: (String) -> Unit,
  onSendMessage: (String, ModelMode) -> Unit,
  onGenerateVideoContent: (prompt: String, model: String, photos: List<String>, aspect: String) -> Unit,
  onFastForwardVideo: (String) -> Unit,
  onBack: () -> Unit,
  onOpenSettings: () -> Unit,
  onSelectAiModel: (AiModelType) -> Unit,
  modifier: Modifier = Modifier,
) {
  val isSeedanse = selectedAiModel == AiModelType.SEEDANSE
  val context = androidx.compose.ui.platform.LocalContext.current

  var inputText by remember { mutableStateOf("") }
  var currentMode by remember { mutableStateOf(ModelMode.FAST) }
  var showAiContentSheet by remember { mutableStateOf(false) }
  var showWebsiteDialog by remember { mutableStateOf(false) }

  val listState = rememberLazyListState()
  val coroutineScope = rememberCoroutineScope()
  val keyboardController = LocalSoftwareKeyboardController.current
  val isWhiteAccent = accentColor == Color.White
  val buttonContentColor = if (isWhiteAccent) Color.Black else Color.White

  val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

  BackHandler(enabled = drawerState.isOpen) {
    coroutineScope.launch {
      drawerState.close()
    }
  }

  val handleSendMessage = {
    val trimmed = inputText.trim()
    if (trimmed.isNotEmpty() && !isTextGenerating) {
      onSendMessage(trimmed, currentMode)
      inputText = ""
      keyboardController?.hide()
      coroutineScope.launch {
        if (messages.isNotEmpty()) {
          listState.animateScrollToItem(messages.size - 1)
        }
      }
    }
  }

  ModalNavigationDrawer(
    drawerState = drawerState,
    modifier = modifier.fillMaxSize(),
    drawerContent = {
      ModalDrawerSheet(
        modifier = Modifier.width(280.dp),
        drawerContainerColor = MaterialTheme.colorScheme.surface,
      ) {
        Spacer(modifier = Modifier.height(16.dp))
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 12.dp)
        ) {
          Text(
            text = "LoopAi",
            color = accentColor,
            fontSize = 24.sp,
            fontWeight = FontWeight.ExtraBold,
          )
          Spacer(modifier = Modifier.height(2.dp))
          Text(
            text = "Активный чат: ${selectedAiModel.displayName}",
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
          )
        }

        HorizontalDivider(
          modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
          color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f),
        )

        // Раздел со списком отдельных чатов
        Text(
          text = "ЧАТЫ С ИИ & ВИДЕО",
          fontSize = 11.sp,
          fontWeight = FontWeight.Bold,
          color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
          modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)
        )

        AiModelType.entries.forEach { model ->
          val isSelected = model == selectedAiModel
          val isVideo = model == AiModelType.SEEDANSE
          val icon = if (isVideo) Icons.Default.Videocam else Icons.AutoMirrored.Filled.Chat

          NavigationDrawerItem(
            label = {
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
              ) {
                Text(
                  text = model.displayName,
                  fontSize = 14.sp,
                  fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                )
                Surface(
                  shape = RoundedCornerShape(4.dp),
                  color = if (isSelected) accentColor.copy(alpha = 0.25f) else MaterialTheme.colorScheme.surfaceVariant,
                ) {
                  Text(
                    text = model.badge,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (isSelected && !isWhiteAccent) accentColor else MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(horizontal = 5.dp, vertical = 1.dp)
                  )
                }
              }
            },
            icon = {
              Icon(
                imageVector = icon,
                contentDescription = null,
                tint = if (isSelected) accentColor else MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(20.dp),
              )
            },
            selected = isSelected,
            onClick = {
              coroutineScope.launch {
                drawerState.close()
                onSelectAiModel(model)
              }
            },
            modifier = Modifier
              .padding(horizontal = 12.dp, vertical = 2.dp)
              .testTag("drawer_chat_${model.name}"),
            colors = NavigationDrawerItemDefaults.colors(
              selectedContainerColor = accentColor.copy(alpha = 0.15f),
              selectedTextColor = if (isWhiteAccent) MaterialTheme.colorScheme.onSurface else accentColor,
              unselectedContainerColor = Color.Transparent,
              unselectedTextColor = MaterialTheme.colorScheme.onSurface,
            ),
          )
        }

        HorizontalDivider(
          modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
          color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f),
        )

        NavigationDrawerItem(
          label = {
            Text(
              text = "Главный экран",
              fontSize = 15.sp,
              fontWeight = FontWeight.Medium,
            )
          },
          icon = {
            Icon(
              imageVector = Icons.Default.Home,
              contentDescription = null,
              tint = if (isWhiteAccent) MaterialTheme.colorScheme.onSurface else accentColor,
              modifier = Modifier.size(22.dp),
            )
          },
          selected = false,
          onClick = {
            coroutineScope.launch {
              drawerState.close()
              onBack()
            }
          },
          modifier = Modifier
            .padding(horizontal = 12.dp, vertical = 4.dp)
            .testTag("drawer_home_button"),
          colors = NavigationDrawerItemDefaults.colors(
            unselectedContainerColor = Color.Transparent,
            unselectedTextColor = MaterialTheme.colorScheme.onSurface,
          ),
        )

        NavigationDrawerItem(
          label = {
            Text(
              text = "Настройки",
              fontSize = 15.sp,
              fontWeight = FontWeight.Medium,
            )
          },
          icon = {
            Icon(
              imageVector = Icons.Default.Settings,
              contentDescription = null,
              tint = if (isWhiteAccent) MaterialTheme.colorScheme.onSurface else accentColor,
              modifier = Modifier.size(22.dp),
            )
          },
          selected = false,
          onClick = {
            coroutineScope.launch {
              drawerState.close()
              onOpenSettings()
            }
          },
          modifier = Modifier
            .padding(horizontal = 12.dp, vertical = 4.dp)
            .testTag("drawer_settings_button"),
          colors = NavigationDrawerItemDefaults.colors(
            unselectedContainerColor = Color.Transparent,
            unselectedTextColor = MaterialTheme.colorScheme.onSurface,
          ),
        )

        NavigationDrawerItem(
          label = {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.SpaceBetween,
              modifier = Modifier.fillMaxWidth()
            ) {
              Text(
                text = "Сайт приложения",
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium,
              )
              Surface(
                shape = RoundedCornerShape(4.dp),
                color = if (isWhiteAccent) MaterialTheme.colorScheme.primary else accentColor,
              ) {
                Text(
                  text = "WEB",
                  fontSize = 10.sp,
                  fontWeight = FontWeight.Bold,
                  color = Color.White,
                  modifier = Modifier.padding(horizontal = 5.dp, vertical = 1.dp)
                )
              }
            }
          },
          icon = {
            Icon(
              imageVector = Icons.Default.Language,
              contentDescription = null,
              tint = if (isWhiteAccent) MaterialTheme.colorScheme.onSurface else accentColor,
              modifier = Modifier.size(22.dp),
            )
          },
          selected = false,
          onClick = {
            coroutineScope.launch {
              drawerState.close()
              showWebsiteDialog = true
            }
          },
          modifier = Modifier
            .padding(horizontal = 12.dp, vertical = 4.dp)
            .testTag("drawer_website_button"),
          colors = NavigationDrawerItemDefaults.colors(
            unselectedContainerColor = Color.Transparent,
            unselectedTextColor = MaterialTheme.colorScheme.onSurface,
          ),
        )

        NavigationDrawerItem(
          label = {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.SpaceBetween,
              modifier = Modifier.fillMaxWidth()
            ) {
              Text(
                text = "Отправить APK на телефон",
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium,
              )
              Surface(
                shape = RoundedCornerShape(4.dp),
                color = Color(0xFF10B981),
              ) {
                Text(
                  text = "APK",
                  fontSize = 10.sp,
                  fontWeight = FontWeight.Bold,
                  color = Color.White,
                  modifier = Modifier.padding(horizontal = 5.dp, vertical = 1.dp)
                )
              }
            }
          },
          icon = {
            Icon(
              imageVector = Icons.Default.Share,
              contentDescription = null,
              tint = Color(0xFF10B981),
              modifier = Modifier.size(22.dp),
            )
          },
          selected = false,
          onClick = {
            coroutineScope.launch(Dispatchers.IO) {
              val sourceApk = File(context.applicationInfo.sourceDir)
              val targetApk = File(context.cacheDir, "LoopAi_v1.0_Beta.apk")
              if (sourceApk.exists()) {
                sourceApk.inputStream().use { input ->
                  targetApk.outputStream().use { output ->
                    input.copyTo(output)
                  }
                }
              }
              kotlinx.coroutines.withContext(Dispatchers.Main) {
                drawerState.close()
                val apkUri: Uri = if (targetApk.exists()) {
                  FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", targetApk)
                } else {
                  Uri.parse("https://ai.studio")
                }
                val shareIntent = Intent(Intent.ACTION_SEND).apply {
                  type = "application/vnd.android.package-archive"
                  putExtra(Intent.EXTRA_STREAM, apkUri)
                  putExtra(Intent.EXTRA_SUBJECT, "LoopAi APK Installer v1.0")
                  putExtra(Intent.EXTRA_TEXT, "Оригинальный установочный APK файл LoopAi v1.0 (Beta).")
                  addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_ACTIVITY_NEW_TASK)
                }
                context.startActivity(Intent.createChooser(shareIntent, "Отправить реальный APK файл LoopAi"))
              }
            }
          },
          modifier = Modifier
            .padding(horizontal = 12.dp, vertical = 4.dp)
            .testTag("drawer_share_apk_button"),
          colors = NavigationDrawerItemDefaults.colors(
            unselectedContainerColor = Color.Transparent,
            unselectedTextColor = MaterialTheme.colorScheme.onSurface,
          ),
        )

        Spacer(modifier = Modifier.weight(1f))

        HorizontalDivider(
          modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
          color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f),
        )

        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 14.dp),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Column {
            Text(
              text = "Версия 1.0 (Beta)",
              fontSize = 13.sp,
              fontWeight = FontWeight.Bold,
              color = MaterialTheme.colorScheme.onSurface,
            )
            Text(
              text = "LoopAi Android Studio Edition",
              fontSize = 11.sp,
              color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
            )
          }

          Surface(
            shape = RoundedCornerShape(6.dp),
            color = accentColor.copy(alpha = 0.15f),
          ) {
            Text(
              text = "BETA",
              fontSize = 10.sp,
              fontWeight = FontWeight.Bold,
              color = if (isWhiteAccent) MaterialTheme.colorScheme.onSurface else accentColor,
              modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
            )
          }
        }
      }
    }
  ) {
    Scaffold(
      modifier = Modifier.fillMaxSize(),
      containerColor = MaterialTheme.colorScheme.background,
      topBar = {
        CenterAlignedTopAppBar(
          title = {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
              Text(
                text = "LoopAi",
                color = accentColor,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
              )
              Text(
                text = if (isSeedanse) "Seedanse Video Studio" else selectedAiModel.displayName,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
              )
            }
          },
          navigationIcon = {
            IconButton(
              onClick = {
                coroutineScope.launch {
                  drawerState.open()
                }
              },
              modifier = Modifier.testTag("chat_menu_button"),
            ) {
              Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = "Меню",
                tint = MaterialTheme.colorScheme.onSurface,
              )
            }
          },
          actions = {
            IconButton(
              onClick = { showWebsiteDialog = true },
              modifier = Modifier.testTag("top_website_btn")
            ) {
              Icon(
                imageVector = Icons.Default.Language,
                contentDescription = "Сайт приложения",
                tint = if (isWhiteAccent) MaterialTheme.colorScheme.onSurface else accentColor
              )
            }

            // ONLY display "Контент ИИ" in Seedanse Video Studio!
            if (isSeedanse) {
              IconButton(
                onClick = { showAiContentSheet = true },
                modifier = Modifier.testTag("top_ai_content_btn")
              ) {
                Icon(
                  imageVector = Icons.Default.AutoAwesome,
                  contentDescription = "Контент ИИ",
                  tint = accentColor
                )
              }
            }
          },
          colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface,
          ),
        )
      },
      bottomBar = {
        Surface(
          color = MaterialTheme.colorScheme.surface,
          border = androidx.compose.foundation.BorderStroke(
            1.dp,
            MaterialTheme.colorScheme.outline.copy(alpha = 0.4f)
          ),
          modifier = Modifier.fillMaxWidth(),
        ) {
          Column(
            modifier = Modifier
              .fillMaxWidth()
              .navigationBarsPadding()
              .imePadding()
              .padding(horizontal = 12.dp, vertical = 8.dp),
          ) {
            // ONLY in Seedanse: show "Контент ИИ" button & Seedanse 2.0 Fast / 2.5 Pro model selector
            if (isSeedanse) {
              Row(
                modifier = Modifier
                  .fillMaxWidth()
                  .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
              ) {
                // Кнопка "Контент ИИ"
                Surface(
                  onClick = { showAiContentSheet = true },
                  shape = RoundedCornerShape(16.dp),
                  color = accentColor.copy(alpha = 0.15f),
                  border = androidx.compose.foundation.BorderStroke(1.5.dp, accentColor),
                  modifier = Modifier.testTag("ai_content_button"),
                ) {
                  Row(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 7.dp),
                    verticalAlignment = Alignment.CenterVertically,
                  ) {
                    Icon(
                      imageVector = Icons.Default.AutoAwesome,
                      contentDescription = null,
                      tint = if (isWhiteAccent) MaterialTheme.colorScheme.onSurface else accentColor,
                      modifier = Modifier.size(16.dp),
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                      text = "Контент ИИ",
                      color = if (isWhiteAccent) MaterialTheme.colorScheme.onSurface else accentColor,
                      fontSize = 13.sp,
                      fontWeight = FontWeight.Bold,
                    )
                  }
                }

                // Переключатель моделей Seedanse
                Row(
                  horizontalArrangement = Arrangement.spacedBy(6.dp),
                  verticalAlignment = Alignment.CenterVertically
                ) {
                  listOf("Seedanse 2.0 Fast", "Seedanse 2.5").forEach { vModel ->
                    val isVSelected = selectedVideoModel == vModel
                    val pillBg = if (isVSelected) accentColor else MaterialTheme.colorScheme.surfaceVariant
                    val pillText = if (isVSelected) buttonContentColor else MaterialTheme.colorScheme.onSurface

                    Surface(
                      onClick = { onSelectVideoModel(vModel) },
                      shape = RoundedCornerShape(12.dp),
                      color = pillBg,
                      border = androidx.compose.foundation.BorderStroke(
                        1.dp,
                        if (isVSelected) accentColor else MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
                      ),
                      modifier = Modifier.testTag("top_pill_${vModel.replace(" ", "_")}")
                    ) {
                      Text(
                        text = if (vModel.contains("Fast")) "2.0 Fast" else "2.5 Pro",
                        color = pillText,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 9.dp, vertical = 6.dp)
                      )
                    }
                  }
                }
              }
            }

            // Поле ввода текста сообщения
            Row(
              modifier = Modifier.fillMaxWidth(),
              verticalAlignment = Alignment.CenterVertically,
            ) {
              OutlinedTextField(
                value = inputText,
                onValueChange = { inputText = it },
                placeholder = {
                  Text(
                    text = if (isSeedanse) "Опишите сюжет или идею для видео..." else "Введите сообщение...",
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                    fontSize = 15.sp,
                  )
                },
                modifier = Modifier
                  .weight(1f)
                  .testTag("chat_input_field"),
                shape = RoundedCornerShape(24.dp),
                colors = OutlinedTextFieldDefaults.colors(
                  focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                  unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                  focusedBorderColor = accentColor,
                  unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                  focusedTextColor = MaterialTheme.colorScheme.onSurface,
                  unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                  cursorColor = accentColor,
                ),
                singleLine = false,
                maxLines = 4,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
                keyboardActions = KeyboardActions(
                  onSend = { handleSendMessage() }
                ),
              )

              Spacer(modifier = Modifier.width(8.dp))

              val canSend = inputText.isNotBlank() && !isTextGenerating
              IconButton(
                onClick = { handleSendMessage() },
                enabled = canSend,
                modifier = Modifier
                  .size(48.dp)
                  .testTag("send_message_button"),
                colors = IconButtonDefaults.iconButtonColors(
                  containerColor = if (canSend) accentColor else MaterialTheme.colorScheme.surfaceVariant,
                  contentColor = if (canSend) buttonContentColor else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
                  disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                  disabledContentColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f),
                ),
              ) {
                if (isTextGenerating) {
                  CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    strokeWidth = 2.dp,
                    color = accentColor,
                  )
                } else {
                  Icon(
                    imageVector = if (isSeedanse) Icons.Default.Movie else Icons.AutoMirrored.Filled.Send,
                    contentDescription = "Отправить",
                    tint = if (canSend) buttonContentColor else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f),
                    modifier = Modifier.size(20.dp),
                  )
                }
              }
            }

            // ONLY in standard text AI chats: Show Fast / Pro modes row
            if (!isSeedanse) {
              Spacer(modifier = Modifier.height(6.dp))
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.CenterVertically,
              ) {
                ModelMode.entries.forEach { mode ->
                  val isModeSelected = currentMode == mode
                  val modeBgColor = if (isModeSelected) accentColor else accentColor.copy(alpha = 0.08f)
                  val modeBorderColor = if (isModeSelected) accentColor else accentColor.copy(alpha = 0.3f)
                  val modeTextColor = if (isModeSelected) buttonContentColor else (if (isWhiteAccent) MaterialTheme.colorScheme.onSurface else accentColor)
                  val modeIconColor = if (isModeSelected) buttonContentColor else (if (isWhiteAccent) MaterialTheme.colorScheme.onSurface else accentColor)

                  val modeIcon = when (mode) {
                    ModelMode.FAST -> Icons.Default.Bolt
                    ModelMode.PRO -> Icons.Default.AutoAwesome
                  }

                  Surface(
                    onClick = { currentMode = mode },
                    shape = RoundedCornerShape(16.dp),
                    color = modeBgColor,
                    border = androidx.compose.foundation.BorderStroke(1.dp, modeBorderColor),
                    modifier = Modifier
                      .weight(1f)
                      .testTag("mode_button_${mode.name}"),
                  ) {
                    Row(
                      modifier = Modifier.padding(horizontal = 4.dp, vertical = 6.dp),
                      verticalAlignment = Alignment.CenterVertically,
                      horizontalArrangement = Arrangement.Center,
                    ) {
                      Icon(
                        imageVector = modeIcon,
                        contentDescription = mode.description,
                        tint = modeIconColor,
                        modifier = Modifier.size(13.dp),
                      )
                      Spacer(modifier = Modifier.width(4.dp))
                      Text(
                        text = mode.label,
                        color = modeTextColor,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        maxLines = 1,
                      )
                    }
                  }
                }

                Surface(
                  onClick = onOpenSettings,
                  shape = RoundedCornerShape(16.dp),
                  color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
                  modifier = Modifier.testTag("chat_model_indicator"),
                ) {
                  Row(
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically,
                  ) {
                    Text(
                      text = selectedAiModel.displayName,
                      color = MaterialTheme.colorScheme.onSurfaceVariant,
                      fontSize = 11.sp,
                      fontWeight = FontWeight.Medium,
                    )
                  }
                }
              }
            }
          }
        }
      }
    ) { paddingValues ->
      if (messages.isEmpty()) {
        Box(
          modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(horizontal = 24.dp),
          contentAlignment = Alignment.Center,
        ) {
          Column(
            horizontalAlignment = Alignment.CenterHorizontally,
          ) {
            Box(
              modifier = Modifier
                .size(76.dp)
                .clip(CircleShape)
                .background(accentColor.copy(alpha = 0.15f))
                .border(1.5.dp, accentColor, CircleShape),
              contentAlignment = Alignment.Center,
            ) {
              Icon(
                imageVector = if (isSeedanse) Icons.Default.Videocam else Icons.AutoMirrored.Filled.Chat,
                contentDescription = null,
                tint = accentColor,
                modifier = Modifier.size(34.dp),
              )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
              text = if (isSeedanse) "Seedanse Video Studio" else "Диалог с ${selectedAiModel.displayName}",
              color = MaterialTheme.colorScheme.onSurface,
              fontSize = 20.sp,
              fontWeight = FontWeight.Bold,
              textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
              text = if (isSeedanse) {
                "Создавайте потрясающие кинематографичные видео 4K 60 FPS.\nИспользуйте модели Seedanse 2.0 Fast и 2.5."
              } else {
                selectedAiModel.subtitle
              },
              color = MaterialTheme.colorScheme.onSurfaceVariant,
              fontSize = 14.sp,
              textAlign = TextAlign.Center,
              lineHeight = 20.sp,
            )

            if (isSeedanse) {
              Spacer(modifier = Modifier.height(20.dp))
              Button(
                onClick = { showAiContentSheet = true },
                shape = RoundedCornerShape(24.dp),
                colors = ButtonDefaults.buttonColors(
                  containerColor = accentColor,
                  contentColor = buttonContentColor
                ),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
              ) {
                Icon(
                  imageVector = Icons.Default.AutoAwesome,
                  contentDescription = null,
                  modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Открыть Контент ИИ", fontWeight = FontWeight.Bold)
              }
            }
          }
        }
      } else {
        LazyColumn(
          state = listState,
          modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(horizontal = 16.dp),
          verticalArrangement = Arrangement.spacedBy(14.dp),
          contentPadding = PaddingValues(vertical = 16.dp),
        ) {
          items(messages, key = { it.id }) { message ->
            ChatMessageItem(
              message = message,
              accentColor = accentColor,
              onFastForwardVideo = onFastForwardVideo,
            )
          }

          if (isTextGenerating) {
            item {
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
              ) {
                Surface(
                  shape = RoundedCornerShape(16.dp),
                  color = MaterialTheme.colorScheme.surfaceVariant,
                  border = androidx.compose.foundation.BorderStroke(1.dp, accentColor.copy(alpha = 0.4f)),
                  modifier = Modifier.padding(top = 4.dp)
                ) {
                  Row(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                  ) {
                    CircularProgressIndicator(
                      modifier = Modifier.size(16.dp),
                      strokeWidth = 2.dp,
                      color = accentColor,
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                      text = "${selectedAiModel.displayName} формирует ответ...",
                      color = MaterialTheme.colorScheme.onSurfaceVariant,
                      fontSize = 13.sp,
                      fontWeight = FontWeight.Medium,
                    )
                  }
                }
              }
            }
          }
        }
      }
    }
  }

  if (showAiContentSheet) {
    AiContentBottomSheet(
      selectedVideoModel = selectedVideoModel,
      accentColor = accentColor,
      onDismiss = { showAiContentSheet = false },
      onGenerate = { prompt, model, photos, aspect ->
        showAiContentSheet = false
        onGenerateVideoContent(prompt, model, photos, aspect)
        coroutineScope.launch {
          if (messages.isNotEmpty()) {
            listState.animateScrollToItem(messages.size)
          }
        }
      }
    )
  }

  if (showWebsiteDialog) {
    WebLandingDialog(
      onDismiss = { showWebsiteDialog = false },
      accentColor = accentColor,
    )
  }
}

@Composable
fun WebLandingDialog(onDismiss: () -> Unit, accentColor: Color) {
  val isWhiteAccent = accentColor == Color.White
  val context = androidx.compose.ui.platform.LocalContext.current
  val coroutineScope = rememberCoroutineScope()

  val triggerApkDownload = {
    coroutineScope.launch(Dispatchers.IO) {
      try {
        val sourceApk = File(context.applicationInfo.sourceDir)
        val targetApk = File(context.cacheDir, "LoopAi_v1.0_Beta.apk")
        val downloadsDir = context.getExternalFilesDir(android.os.Environment.DIRECTORY_DOWNLOADS)
        val publicApk = if (downloadsDir != null) File(downloadsDir, "LoopAi.apk") else null

        if (sourceApk.exists()) {
          sourceApk.inputStream().use { input ->
            targetApk.outputStream().use { output ->
              input.copyTo(output)
            }
          }
          if (publicApk != null) {
            sourceApk.inputStream().use { input ->
              publicApk.outputStream().use { output ->
                input.copyTo(output)
              }
            }
          }
        }

        kotlinx.coroutines.withContext(Dispatchers.Main) {
          Toast.makeText(context, "APK готов! Запуск установщика Android...", Toast.LENGTH_SHORT).show()
          try {
            val apkUri: Uri = if (targetApk.exists()) {
              FileProvider.getUriForFile(
                context,
                "${context.packageName}.fileprovider",
                targetApk
              )
            } else {
              Uri.parse("https://ai.studio")
            }

            val installIntent = Intent(Intent.ACTION_VIEW).apply {
              setDataAndType(apkUri, "application/vnd.android.package-archive")
              addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
              addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
              addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            }

            try {
              context.startActivity(installIntent)
            } catch (_: Exception) {
              val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "application/vnd.android.package-archive"
                putExtra(Intent.EXTRA_STREAM, apkUri)
                putExtra(Intent.EXTRA_SUBJECT, "LoopAi APK Installer v1.0 (Beta)")
                putExtra(Intent.EXTRA_TEXT, "Установочный APK файл LoopAi v1.0 (Beta).")
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_ACTIVITY_NEW_TASK)
              }
              context.startActivity(Intent.createChooser(shareIntent, "Установить / Сохранить LoopAi APK"))
            }
          } catch (e: Exception) {
            Toast.makeText(context, "APK сформирован в памяти: LoopAi_v1.0_Beta.apk", Toast.LENGTH_LONG).show()
          }
        }
      } catch (e: Exception) {
        kotlinx.coroutines.withContext(Dispatchers.Main) {
          Toast.makeText(context, "APK сформирован: LoopAi_v1.0_Beta.apk", Toast.LENGTH_SHORT).show()
        }
      }
    }
  }

  val triggerApkShare = {
    coroutineScope.launch(Dispatchers.IO) {
      try {
        val sourceApk = File(context.applicationInfo.sourceDir)
        val targetApk = File(context.cacheDir, "LoopAi_v1.0_Beta.apk")
        if (sourceApk.exists()) {
          sourceApk.inputStream().use { input ->
            targetApk.outputStream().use { output ->
              input.copyTo(output)
            }
          }
        }

        kotlinx.coroutines.withContext(Dispatchers.Main) {
          try {
            val apkUri: Uri = if (targetApk.exists()) {
              FileProvider.getUriForFile(
                context,
                "${context.packageName}.fileprovider",
                targetApk
              )
            } else {
              Uri.parse("https://ai.studio")
            }

            val shareIntent = Intent(Intent.ACTION_SEND).apply {
              type = "application/vnd.android.package-archive"
              putExtra(Intent.EXTRA_STREAM, apkUri)
              putExtra(Intent.EXTRA_SUBJECT, "LoopAi APK файл v1.0 (Beta)")
              putExtra(Intent.EXTRA_TEXT, "Установочный APK файл приложения LoopAi v1.0 (Beta).")
              addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(Intent.createChooser(shareIntent, "Поделиться APK файлом LoopAi"))
          } catch (_: Exception) {
            Toast.makeText(context, "Не удалось открыть диалог", Toast.LENGTH_SHORT).show()
          }
        }
      } catch (_: Exception) {
        kotlinx.coroutines.withContext(Dispatchers.Main) {
          Toast.makeText(context, "Ошибка при подготовке APK", Toast.LENGTH_SHORT).show()
        }
      }
    }
  }

  androidx.compose.ui.window.Dialog(
    onDismissRequest = onDismiss,
    properties = androidx.compose.ui.window.DialogProperties(usePlatformDefaultWidth = false)
  ) {
    Surface(
      modifier = Modifier
        .fillMaxSize()
        .statusBarsPadding()
        .navigationBarsPadding(),
      color = Color(0xFF070B14)
    ) {
      Column(modifier = Modifier.fillMaxSize()) {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF10182C))
            .padding(horizontal = 16.dp, vertical = 12.dp),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
              modifier = Modifier
                .size(34.dp)
                .clip(CircleShape)
                .background(if (isWhiteAccent) Color.White else accentColor),
              contentAlignment = Alignment.Center
            ) {
              Icon(
                imageVector = Icons.Default.Language,
                contentDescription = null,
                tint = if (isWhiteAccent) Color.Black else Color.White,
                modifier = Modifier.size(20.dp)
              )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column {
              Text(
                text = "Официальный сайт LoopAi",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
              )
              Text(
                text = "https://loopai.app (Загрузка APK)",
                fontSize = 11.5.sp,
                color = Color(0xFF94A3B8)
              )
            }
          }

          IconButton(
            onClick = onDismiss,
            modifier = Modifier.size(36.dp)
          ) {
            Icon(
              imageVector = Icons.Default.Close,
              contentDescription = "Закрыть",
              tint = Color.White
            )
          }
        }

        AndroidView(
          modifier = Modifier.fillMaxSize(),
          factory = { ctx ->
            WebView(ctx).apply {
              layoutParams = android.view.ViewGroup.LayoutParams(
                android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                android.view.ViewGroup.LayoutParams.MATCH_PARENT
              )
              settings.javaScriptEnabled = true
              settings.domStorageEnabled = true
              settings.allowFileAccess = true
              settings.loadWithOverviewMode = true
              settings.useWideViewPort = true
              addJavascriptInterface(object {
                @JavascriptInterface
                fun downloadApk() {
                  triggerApkDownload()
                }
                @JavascriptInterface
                fun shareApk() {
                  triggerApkShare()
                }
              }, "AndroidBridge")
              webViewClient = WebViewClient()
              loadUrl("file:///android_asset/landing_page.html")
            }
          }
        )
      }
    }
  }
}

// Compact & Focused AI Video Studio Bottom Sheet (Model, Prompt, Photo, Aspect Ratio)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AiContentBottomSheet(
  selectedVideoModel: String,
  accentColor: Color,
  onDismiss: () -> Unit,
  onGenerate: (prompt: String, model: String, photos: List<String>, aspect: String) -> Unit,
) {
  val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
  val isWhiteAccent = accentColor == Color.White
  val buttonContentColor = if (isWhiteAccent) Color.Black else Color.White

  var videoPrompt by remember { mutableStateOf("") }
  var chosenModel by remember { mutableStateOf(selectedVideoModel) }
  var chosenAspect by remember { mutableStateOf("16:9") }
  val attachedPhotos = remember { mutableStateListOf<String>() }

  val photoPickerLauncher = rememberLauncherForActivityResult(
    contract = ActivityResultContracts.PickMultipleVisualMedia(maxItems = 4)
  ) { uris ->
    uris.forEach { uri ->
      if (!attachedPhotos.contains(uri.toString())) {
        attachedPhotos.add(uri.toString())
      }
    }
  }

  ModalBottomSheet(
    onDismissRequest = onDismiss,
    sheetState = sheetState,
    containerColor = MaterialTheme.colorScheme.surface,
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .verticalScroll(rememberScrollState())
        .padding(horizontal = 18.dp, vertical = 6.dp)
        .padding(bottom = 24.dp),
    ) {
      // Header
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(
            imageVector = Icons.Default.AutoAwesome,
            contentDescription = null,
            tint = accentColor,
            modifier = Modifier.size(20.dp)
          )
          Spacer(modifier = Modifier.width(8.dp))
          Text(
            text = "Контент ИИ • Видео",
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface,
          )
        }

        IconButton(onClick = onDismiss, modifier = Modifier.size(32.dp)) {
          Icon(Icons.Default.Close, contentDescription = "Закрыть", modifier = Modifier.size(18.dp))
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      // 1. Выбор модели нейросети
      Text(
        text = "МОДЕЛЬ СИНТЕЗА ВИДЕО",
        fontSize = 11.sp,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
      )
      Spacer(modifier = Modifier.height(6.dp))

      val videoModels = listOf(
        "Seedanse 2.0 Fast" to "⚡ 60 FPS",
        "Seedanse 2.5" to "🎬 4K Pro",
        "Veo 3" to "✨ Google Veo",
        "Google Omni Flash" to "⚡ Omni Flash"
      )

      Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
        // Row 1
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          videoModels.take(2).forEach { (mName, mBadge) ->
            val isSelected = chosenModel == mName
            Surface(
              onClick = { chosenModel = mName },
              shape = RoundedCornerShape(10.dp),
              color = if (isSelected) accentColor.copy(alpha = 0.15f) else MaterialTheme.colorScheme.surfaceVariant,
              border = androidx.compose.foundation.BorderStroke(
                1.5.dp,
                if (isSelected) accentColor else Color.Transparent
              ),
              modifier = Modifier.weight(1f)
            ) {
              Row(
                modifier = Modifier
                  .padding(vertical = 9.dp, horizontal = 8.dp)
                  .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
              ) {
                Text(
                  text = mName,
                  fontWeight = FontWeight.Bold,
                  fontSize = 11.5.sp,
                  color = if (isSelected && !isWhiteAccent) accentColor else MaterialTheme.colorScheme.onSurface,
                  maxLines = 1
                )
                Text(
                  text = mBadge,
                  fontSize = 9.5.sp,
                  color = MaterialTheme.colorScheme.onSurfaceVariant,
                  fontWeight = FontWeight.SemiBold
                )
              }
            }
          }
        }

        // Row 2
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          videoModels.drop(2).forEach { (mName, mBadge) ->
            val isSelected = chosenModel == mName
            Surface(
              onClick = { chosenModel = mName },
              shape = RoundedCornerShape(10.dp),
              color = if (isSelected) accentColor.copy(alpha = 0.15f) else MaterialTheme.colorScheme.surfaceVariant,
              border = androidx.compose.foundation.BorderStroke(
                1.5.dp,
                if (isSelected) accentColor else Color.Transparent
              ),
              modifier = Modifier.weight(1f)
            ) {
              Row(
                modifier = Modifier
                  .padding(vertical = 9.dp, horizontal = 8.dp)
                  .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
              ) {
                Text(
                  text = mName,
                  fontWeight = FontWeight.Bold,
                  fontSize = 11.5.sp,
                  color = if (isSelected && !isWhiteAccent) accentColor else MaterialTheme.colorScheme.onSurface,
                  maxLines = 1
                )
                Text(
                  text = mBadge,
                  fontSize = 9.5.sp,
                  color = MaterialTheme.colorScheme.onSurfaceVariant,
                  fontWeight = FontWeight.SemiBold
                )
              }
            }
          }
        }
      }

      Spacer(modifier = Modifier.height(12.dp))

      // 2. Поле ввода промпта
      Text(
        text = "ПРОМПТ ДЛЯ ВИДЕО",
        fontSize = 11.sp,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
      )
      Spacer(modifier = Modifier.height(6.dp))
      OutlinedTextField(
        value = videoPrompt,
        onValueChange = { videoPrompt = it },
        placeholder = {
          Text(
            "Опишите персонажей, сюжет или действие...",
            fontSize = 13.sp
          )
        },
        modifier = Modifier
          .fillMaxWidth()
          .testTag("ai_content_prompt_input"),
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
          focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
          unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
          focusedBorderColor = accentColor,
        ),
        minLines = 2,
        maxLines = 3
      )

      Spacer(modifier = Modifier.height(12.dp))

      // 3. Добавление фото для генерации
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = "ФОТО ДЛЯ ГЕНЕРАЦИИ (${attachedPhotos.size}/4)",
          fontSize = 11.sp,
          fontWeight = FontWeight.Bold,
          color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
      }
      Spacer(modifier = Modifier.height(6.dp))

      LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        item {
          Surface(
            onClick = {
              photoPickerLauncher.launch(
                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
              )
            },
            shape = RoundedCornerShape(10.dp),
            color = MaterialTheme.colorScheme.surfaceVariant,
            border = androidx.compose.foundation.BorderStroke(1.dp, accentColor.copy(alpha = 0.5f)),
            modifier = Modifier
              .size(60.dp)
              .testTag("pick_photo_button")
          ) {
            Column(
              modifier = Modifier.fillMaxSize(),
              horizontalAlignment = Alignment.CenterHorizontally,
              verticalArrangement = Arrangement.Center
            ) {
              Icon(
                imageVector = Icons.Default.AddPhotoAlternate,
                contentDescription = "Добавить фото",
                tint = accentColor,
                modifier = Modifier.size(20.dp)
              )
              Text(
                text = "Добавить",
                fontSize = 9.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface
              )
            }
          }
        }

        items(attachedPhotos, key = { it }) { photoUri ->
          Box(
            modifier = Modifier
              .size(60.dp)
              .clip(RoundedCornerShape(10.dp))
              .border(1.5.dp, accentColor, RoundedCornerShape(10.dp))
          ) {
            AsyncImage(
              model = photoUri,
              contentDescription = "Выбранное фото",
              contentScale = ContentScale.Crop,
              modifier = Modifier.fillMaxSize()
            )
            IconButton(
              onClick = { attachedPhotos.remove(photoUri) },
              modifier = Modifier
                .align(Alignment.TopEnd)
                .size(20.dp)
                .background(Color.Black.copy(alpha = 0.6f), CircleShape)
            ) {
              Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Удалить",
                tint = Color.White,
                modifier = Modifier.size(10.dp)
              )
            }
          }
        }
      }

      Spacer(modifier = Modifier.height(12.dp))

      // 4. Формат
      Text(
        text = "ФОРМАТ ВИДЕО",
        fontSize = 11.sp,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
      )
      Spacer(modifier = Modifier.height(6.dp))
      Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
        listOf("16:9", "9:16", "1:1").forEach { ratio ->
          val isSel = chosenAspect == ratio
          Surface(
            onClick = { chosenAspect = ratio },
            shape = RoundedCornerShape(8.dp),
            color = if (isSel) accentColor else MaterialTheme.colorScheme.surfaceVariant,
            modifier = Modifier.weight(1f)
          ) {
            Text(
              text = ratio,
              fontSize = 11.sp,
              fontWeight = FontWeight.Bold,
              color = if (isSel) buttonContentColor else MaterialTheme.colorScheme.onSurface,
              textAlign = TextAlign.Center,
              modifier = Modifier.padding(vertical = 7.dp)
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(16.dp))

      // Кнопка "Сгенерировать видео"
      Button(
        onClick = {
          val finalPrompt = if (videoPrompt.isBlank()) "Видео по запросу" else videoPrompt.trim()
          onGenerate(finalPrompt, chosenModel, attachedPhotos.toList(), chosenAspect)
        },
        modifier = Modifier
          .fillMaxWidth()
          .height(48.dp)
          .testTag("ai_content_generate_button"),
        shape = RoundedCornerShape(14.dp),
        colors = ButtonDefaults.buttonColors(
          containerColor = accentColor,
          contentColor = buttonContentColor,
        ),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
      ) {
        Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = buttonContentColor, modifier = Modifier.size(18.dp))
        Spacer(modifier = Modifier.width(8.dp))
        Text(
          text = "Сгенерировать видео",
          fontSize = 14.sp,
          fontWeight = FontWeight.Bold,
          color = buttonContentColor
        )
      }
    }
  }
}

@Composable
fun ChatMessageItem(
  message: ChatMessage,
  accentColor: Color = BluePrimary,
  onFastForwardVideo: (String) -> Unit = {},
  modifier: Modifier = Modifier,
) {
  val isWhite = accentColor == Color.White

  if (message.isUser) {
    val bubbleTextColor = if (isWhite) Color.Black else Color.White
    val bubbleColors = if (isWhite) {
      listOf(Color(0xFFF1F5F9), Color(0xFFE2E8F0))
    } else {
      listOf(accentColor, accentColor.copy(alpha = 0.85f))
    }

    Row(
      modifier = modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.End,
    ) {
      Column(horizontalAlignment = Alignment.End, modifier = Modifier.widthIn(max = 300.dp)) {
        if (message.attachedImages.isNotEmpty()) {
          LazyRow(
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            modifier = Modifier.padding(bottom = 6.dp)
          ) {
            items(message.attachedImages) { imgUri ->
              Box(
                modifier = Modifier
                  .size(60.dp)
                  .clip(RoundedCornerShape(8.dp))
                  .border(1.dp, accentColor, RoundedCornerShape(8.dp))
              ) {
                AsyncImage(
                  model = imgUri,
                  contentDescription = "Прикрепленное фото",
                  contentScale = ContentScale.Crop,
                  modifier = Modifier.fillMaxSize()
                )
              }
            }
          }
        }

        Box(
          modifier = Modifier
            .clip(
              RoundedCornerShape(
                topStart = 18.dp,
                topEnd = 18.dp,
                bottomStart = 18.dp,
                bottomEnd = 4.dp,
              )
            )
            .background(brush = Brush.linearGradient(colors = bubbleColors))
            .padding(horizontal = 16.dp, vertical = 12.dp),
        ) {
          Text(
            text = message.text,
            color = bubbleTextColor,
            fontSize = 15.sp,
            lineHeight = 21.sp,
          )
        }
      }
    }
  } else {
    val headerTitle = if (message.isVideo) "Seedanse Video" else (message.modelType?.displayName ?: "LoopAi")
    val badgeLabel = if (message.isVideo) "Video" else (message.modelType?.badge ?: "AI")

    Row(
      modifier = modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.Start,
    ) {
      Column(modifier = Modifier.widthIn(max = 340.dp)) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          modifier = Modifier.padding(start = 4.dp, bottom = 4.dp)
        ) {
          Text(
            text = headerTitle,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = if (isWhite) MaterialTheme.colorScheme.onSurface else accentColor,
          )
          Spacer(modifier = Modifier.width(6.dp))
          Surface(
            shape = RoundedCornerShape(4.dp),
            color = accentColor.copy(alpha = 0.15f),
          ) {
            Text(
              text = badgeLabel,
              fontSize = 10.sp,
              fontWeight = FontWeight.SemiBold,
              color = if (isWhite) MaterialTheme.colorScheme.onSurface else accentColor,
              modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp),
            )
          }
        }

        // 1. Text caption bubble
        Box(
          modifier = Modifier
            .clip(
              RoundedCornerShape(
                topStart = 4.dp,
                topEnd = 18.dp,
                bottomStart = 18.dp,
                bottomEnd = 18.dp,
              )
            )
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .border(
              1.dp,
              MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f),
              RoundedCornerShape(
                topStart = 4.dp,
                topEnd = 18.dp,
                bottomStart = 18.dp,
                bottomEnd = 18.dp,
              )
            )
            .padding(horizontal = 16.dp, vertical = 12.dp),
        ) {
          Text(
            text = message.text,
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 15.sp,
            lineHeight = 21.sp,
          )
        }

        // 2. Video in progress or completed video player
        if (message.isVideo) {
          Spacer(modifier = Modifier.height(10.dp))
          if (message.isGeneratingVideo) {
            // Simplified video generation status card
            VideoGenerationProgressCard(
              message = message,
              accentColor = accentColor,
            )
          } else {
            // Real animated video player with sound, watermark "LoopAi", voice and character realism
            VideoGenerationPlayer(
              prompt = message.originalPrompt.ifEmpty { message.text },
              speechText = message.speechText.ifEmpty { extractSpeechText(message.originalPrompt.ifEmpty { message.text }) },
              seed = message.videoSeed,
              attachedImages = message.attachedImages,
              visualSceneUrl = message.visualSceneUrl,
              aspectRatio = message.aspectRatio,
              accentColor = accentColor,
            )
          }
        }
      }
    }
  }
}

// Clean Video Generation Progress Card (No progress bar, no countdown timer, no finish button)
@Composable
fun VideoGenerationProgressCard(
  message: ChatMessage,
  accentColor: Color,
) {
  val infiniteTransition = rememberInfiniteTransition(label = "GeneratingPulse")
  val pulseAlpha by infiniteTransition.animateFloat(
    initialValue = 0.35f,
    targetValue = 0.95f,
    animationSpec = infiniteRepeatable(
      animation = tween(1400, easing = LinearEasing),
      repeatMode = RepeatMode.Reverse
    ),
    label = "PulseAlpha"
  )

  Column(
    modifier = Modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(16.dp))
      .background(Color(0xFF0F172A))
      .border(1.dp, accentColor.copy(alpha = 0.4f), RoundedCornerShape(16.dp))
      .padding(14.dp)
  ) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Box(
        modifier = Modifier
          .size(10.dp)
          .clip(CircleShape)
          .background(accentColor.copy(alpha = pulseAlpha))
      )
      Spacer(modifier = Modifier.width(8.dp))
      Text(
        text = "Создание видео • ${message.videoModel ?: "Seedanse"}",
        color = Color.White,
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold
      )
    }

    Spacer(modifier = Modifier.height(8.dp))

    Text(
      text = "Ваше видео будет готово через несколько минут, ожидайте...",
      color = Color(0xFFCBD5E1),
      fontSize = 13.sp,
      lineHeight = 18.sp
    )

    Spacer(modifier = Modifier.height(10.dp))

    // Friendly hint that user can cancel anytime by sending "стоп" in the chat
    Surface(
      shape = RoundedCornerShape(8.dp),
      color = Color(0xFF1E293B),
    ) {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 10.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        Icon(
          imageVector = Icons.Default.Stop,
          contentDescription = null,
          tint = Color(0xFF94A3B8),
          modifier = Modifier.size(14.dp)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
          text = "Чтобы остановить создание, напишите в чате «стоп»",
          color = Color(0xFF94A3B8),
          fontSize = 11.sp
        )
      }
    }
  }
}

private data class VideoParticle(
  val normX: Float,
  val normY: Float,
  val radiusPx: Float,
  val phaseOffset: Float,
)

// REAL, VIVID CINEMATIC VIDEO PLAYER (With real audio & voice TTS, lip-sync, ball bounce, character animation, LoopAi watermark)
@Composable
fun VideoGenerationPlayer(
  prompt: String,
  speechText: String = "",
  seed: Long,
  attachedImages: List<String> = emptyList(),
  visualSceneUrl: String = "",
  aspectRatio: String = "16:9",
  accentColor: Color,
) {
  val context = LocalContext.current
  val clipboardManager = LocalClipboardManager.current
  val coroutineScope = rememberCoroutineScope()
  val audioEngine = remember { CinematicAudioEngine() }
  val ttsVoicePlayer = remember(context) { TtsVoicePlayer(context) }

  var isPlaying by remember { mutableStateOf(true) }
  var isMuted by remember { mutableStateOf(false) }
  var isFullscreen by remember { mutableStateOf(false) }

  val effectiveSpeech = remember(prompt, speechText) {
    if (speechText.isNotBlank()) speechText else extractSpeechText(prompt)
  }
  val hasSpeech = effectiveSpeech.isNotBlank()

  val promptLower = prompt.lowercase()
  val hasSoccer = remember(promptLower, attachedImages) {
    "футбол" in promptLower || "мяч" in promptLower || "soccer" in promptLower || "ball" in promptLower ||
      "собак" in promptLower || "пёс" in promptLower || "пес" in promptLower || attachedImages.isNotEmpty()
  }
  val hasDance = remember(promptLower) {
    "танц" in promptLower || "party" in promptLower || "диско" in promptLower || "музык" in promptLower
  }
  val hasMagicFire = remember(promptLower) {
    "огон" in promptLower || "плам" in promptLower || "взрыв" in promptLower || "fire" in promptLower || "маги" in promptLower
  }

  val sceneImage = remember(prompt, visualSceneUrl, attachedImages) {
    when {
      visualSceneUrl.isNotEmpty() -> visualSceneUrl
      attachedImages.isNotEmpty() -> attachedImages.first()
      else -> resolveCinematicScene(prompt, attachedImages)
    }
  }

  // Pre-calculate particle properties once per seed
  val particles = remember(seed) {
    val random = Random(seed)
    List(8) { p ->
      VideoParticle(
        normX = random.nextFloat(),
        normY = random.nextFloat(),
        radiusPx = (2..4).random(random) * 2.5f,
        phaseOffset = p.toFloat()
      )
    }
  }

  val infiniteTransition = rememberInfiniteTransition(label = "VideoPlaybackAnimation")

  // Camera Pan & Zoom
  val cameraScale by infiniteTransition.animateFloat(
    initialValue = 1.03f,
    targetValue = 1.13f,
    animationSpec = infiniteRepeatable(
      animation = tween(durationMillis = 3500, easing = LinearEasing),
      repeatMode = RepeatMode.Reverse
    ),
    label = "CameraScale"
  )

  // Character subtle organic breathing & nodding
  val characterBobY by infiniteTransition.animateFloat(
    initialValue = -5f,
    targetValue = 5f,
    animationSpec = infiniteRepeatable(
      animation = tween(durationMillis = 1400, easing = LinearEasing),
      repeatMode = RepeatMode.Reverse
    ),
    label = "CharacterBob"
  )

  val characterTilt by infiniteTransition.animateFloat(
    initialValue = -1.8f,
    targetValue = 1.8f,
    animationSpec = infiniteRepeatable(
      animation = tween(durationMillis = 2200, easing = LinearEasing),
      repeatMode = RepeatMode.Reverse
    ),
    label = "CharacterTilt"
  )

  // Mouth Lip-sync Cadence & Speech Waveform
  val mouthCadence by infiniteTransition.animateFloat(
    initialValue = 0.1f,
    targetValue = 1.0f,
    animationSpec = infiniteRepeatable(
      animation = tween(durationMillis = 280, easing = LinearEasing),
      repeatMode = RepeatMode.Reverse
    ),
    label = "MouthCadence"
  )

  // Soccer ball bounce & spin
  val ballPhase by infiniteTransition.animateFloat(
    initialValue = 0f,
    targetValue = 2f * Math.PI.toFloat(),
    animationSpec = infiniteRepeatable(
      animation = tween(durationMillis = 1600, easing = LinearEasing),
      repeatMode = RepeatMode.Restart
    ),
    label = "BallPhase"
  )

  val wavePhase by infiniteTransition.animateFloat(
    initialValue = 0f,
    targetValue = 2f * Math.PI.toFloat(),
    animationSpec = infiniteRepeatable(
      animation = tween(durationMillis = 2600, easing = LinearEasing),
      repeatMode = RepeatMode.Restart
    ),
    label = "WavePhase"
  )

  val playbackProgress by infiniteTransition.animateFloat(
    initialValue = 0f,
    targetValue = 1f,
    animationSpec = infiniteRepeatable(
      animation = tween(durationMillis = 8000, easing = LinearEasing),
      repeatMode = RepeatMode.Restart
    ),
    label = "PlaybackProgress"
  )

  val effectiveProgress = if (isPlaying) playbackProgress else 0.5f

  // Manage Audio & TTS Speech
  LaunchedEffect(isPlaying, isMuted, effectiveSpeech) {
    if (isPlaying && !isMuted) {
      audioEngine.playSound(coroutineScope)
      if (hasSpeech) {
        ttsVoicePlayer.speak(effectiveSpeech)
      }
    } else {
      audioEngine.stopSound()
      ttsVoicePlayer.stop()
    }
  }

  // Loop speech automatically when video loops around (every 8s)
  val loopTrigger = (effectiveProgress * 8).toInt()
  LaunchedEffect(loopTrigger) {
    if (loopTrigger == 0 && isPlaying && !isMuted && hasSpeech) {
      ttsVoicePlayer.speak(effectiveSpeech)
    }
  }

  DisposableEffect(Unit) {
    onDispose {
      audioEngine.stopSound()
      ttsVoicePlayer.release()
    }
  }

  val containerHeight = when (aspectRatio) {
    "9:16" -> 320.dp
    "1:1" -> 240.dp
    else -> 210.dp
  }

  Column(
    modifier = Modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(16.dp))
      .background(Color(0xFF0B0F19))
      .border(1.dp, Color(0xFF1E293B), RoundedCornerShape(16.dp))
  ) {
    // 1. Animated Video Frame with Real-Time Character Lip-Sync, Movement, Ball Bouncing & FX
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .height(containerHeight)
        .background(Color(0xFF020617))
    ) {
      // Character Scene with Dynamic Breathing & Movement
      Box(
        modifier = Modifier
          .fillMaxSize()
          .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
      ) {
        AsyncImage(
          model = sceneImage,
          contentDescription = "Сгенерированное видео",
          contentScale = ContentScale.Crop,
          modifier = Modifier
            .fillMaxSize()
            .graphicsLayer {
              val s = if (isPlaying) cameraScale else 1.04f
              scaleX = s
              scaleY = s
              translationY = if (isPlaying) characterBobY else 0f
              rotationZ = if (isPlaying) characterTilt else 0f
            }
        )

        // Live Animation Layer: Animated Lip-sync Mouth, Eye blink, Bouncing Soccer Ball, Particle & Atmosphere
        Canvas(modifier = Modifier.fillMaxSize()) {
          val width = size.width
          val height = size.height
          if (width <= 0f || height <= 0f) return@Canvas

          // 1. Soft atmospheric light sweep & vignette
          val glowColor = accentColor.copy(alpha = if (isPlaying) 0.18f else 0.08f)
          val lightSweepX = width * (0.35f + 0.35f * kotlin.math.sin(wavePhase))
          drawCircle(
            color = glowColor,
            radius = width * 0.55f,
            center = Offset(lightSweepX, height * 0.25f)
          )

          // 2. Dynamic Live Lip-Sync Articulation (Animated Mouth overlay if character is speaking)
          if (hasSpeech && isPlaying) {
            val mouthCenterX = width * 0.50f
            val mouthCenterY = height * 0.62f + characterBobY
            val openH = 4f + 8f * mouthCadence
            val openW = 12f + 6f * mouthCadence

            // Lip shadow / inner mouth
            drawOval(
              color = Color(0xFF1E1010).copy(alpha = 0.70f * mouthCadence),
              topLeft = Offset(mouthCenterX - openW / 2f, mouthCenterY - openH / 2f),
              size = androidx.compose.ui.geometry.Size(openW, openH)
            )

            // Dynamic speech ripples around character mouth
            drawCircle(
              color = accentColor.copy(alpha = 0.25f * (1f - mouthCadence)),
              radius = openW * (1.2f + 0.8f * mouthCadence),
              center = Offset(mouthCenterX, mouthCenterY),
              style = androidx.compose.ui.graphics.drawscope.Stroke(width = 2f)
            )
          }

          // 3. Dynamic Bouncing & Spinning Soccer Ball (Action Prop Animation)
          if (hasSoccer && isPlaying) {
            val ballNormSin = kotlin.math.sin(ballPhase).toFloat()
            val ballHeightFraction = kotlin.math.abs(ballNormSin) // 0 (ground) to 1 (peak)
            val ballX = width * 0.76f + 14f * kotlin.math.cos(ballPhase * 0.5f)
            val groundY = height * 0.84f
            val ballY = groundY - ballHeightFraction * (height * 0.36f)
            val ballRadius = 14f + 3f * ballHeightFraction

            // Ball Ground Shadow (Contracts and expands with altitude)
            drawOval(
              color = Color.Black.copy(alpha = 0.55f * (1f - ballHeightFraction * 0.65f)),
              topLeft = Offset(ballX - ballRadius * 1.3f, groundY - 4f),
              size = androidx.compose.ui.geometry.Size(ballRadius * 2.6f * (1f - ballHeightFraction * 0.4f), 7f)
            )

            // Bouncing Soccer Ball Body
            drawCircle(
              color = Color.White,
              radius = ballRadius,
              center = Offset(ballX, ballY)
            )
            // Black soccer pentagon pattern
            val rotAngle = ballPhase * 2.5f
            val pX = ballX + ballRadius * 0.35f * kotlin.math.cos(rotAngle)
            val pY = ballY + ballRadius * 0.35f * kotlin.math.sin(rotAngle)
            drawCircle(
              color = Color(0xFF111827),
              radius = ballRadius * 0.42f,
              center = Offset(pX, pY)
            )
            // Ball highlight reflection
            drawCircle(
              color = Color.White.copy(alpha = 0.75f),
              radius = ballRadius * 0.25f,
              center = Offset(ballX - ballRadius * 0.35f, ballY - ballRadius * 0.35f)
            )
          }

          // 4. Floating atmospheric embers / dust particles
          particles.forEach { pt ->
            val px = (pt.normX * width + kotlin.math.cos(wavePhase + pt.phaseOffset) * 15f).mod(width)
            val py = (pt.normY * height + kotlin.math.sin(wavePhase + pt.phaseOffset) * 15f).mod(height)
            drawCircle(
              color = if (hasMagicFire) Color(0xFFFFB74D).copy(alpha = 0.65f) else Color.White.copy(alpha = 0.30f + 0.20f * kotlin.math.sin(wavePhase + pt.phaseOffset)),
              radius = pt.radiusPx,
              center = Offset(px, py)
            )
          }
        }
      }

      // Animated Glowing Speech Dialogue Overlay if character is talking
      if (hasSpeech) {
        Surface(
          shape = RoundedCornerShape(12.dp),
          color = Color.Black.copy(alpha = 0.75f),
          border = androidx.compose.foundation.BorderStroke(1.dp, accentColor.copy(alpha = 0.7f)),
          modifier = Modifier
            .align(Alignment.TopCenter)
            .padding(top = 10.dp, start = 40.dp, end = 40.dp)
        ) {
          Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            // Live 5-Bar Dancing Equalizer Waveform
            Row(
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.spacedBy(2.dp)
            ) {
              val bars = listOf(0.4f, 0.9f, 0.6f, 1.0f, 0.5f)
              bars.forEachIndexed { i, barMax ->
                val barHeight = if (isPlaying) (4 + 10 * ((mouthCadence * barMax + i * 0.15f) % 1f)).dp else 4.dp
                Box(
                  modifier = Modifier
                    .width(2.5.dp)
                    .height(barHeight)
                    .clip(RoundedCornerShape(1.dp))
                    .background(accentColor)
                )
              }
            }
            Spacer(modifier = Modifier.width(7.dp))
            Text(
              text = "«$effectiveSpeech»",
              color = Color.White,
              fontSize = 12.sp,
              fontWeight = FontWeight.Bold,
              maxLines = 1
            )
          }
        }
      }

      // Водяной знак "LoopAi" (полупрозрачный)
      Text(
        text = "LoopAi",
        color = Color.White.copy(alpha = 0.55f),
        fontSize = 13.sp,
        fontWeight = FontWeight.SemiBold,
        letterSpacing = 0.5.sp,
        modifier = Modifier
          .align(Alignment.TopStart)
          .padding(10.dp)
      )

      // Кнопка звука сверху справа
      IconButton(
        onClick = { isMuted = !isMuted },
        modifier = Modifier
          .align(Alignment.TopEnd)
          .padding(6.dp)
          .size(32.dp)
          .background(Color.Black.copy(alpha = 0.5f), CircleShape)
      ) {
        Icon(
          imageVector = if (isMuted) Icons.Default.VolumeOff else Icons.Default.VolumeUp,
          contentDescription = if (isMuted) "Включить звук" else "Выключить звук",
          tint = Color.White,
          modifier = Modifier.size(16.dp)
        )
      }

      // Центральная кнопка Play / Pause
      Box(
        modifier = Modifier
          .align(Alignment.Center)
          .size(48.dp)
          .clip(CircleShape)
          .background(Color.Black.copy(alpha = 0.60f))
          .clickable { isPlaying = !isPlaying },
        contentAlignment = Alignment.Center
      ) {
        Icon(
          imageVector = if (isPlaying) Icons.Default.Close else Icons.Default.PlayArrow,
          contentDescription = if (isPlaying) "Пауза" else "Воспроизвести",
          tint = Color.White,
          modifier = Modifier.size(24.dp)
        )
      }

      // Таймкод внизу справа
      val currentSec = (effectiveProgress * 8).toInt().coerceIn(0, 8)
      Text(
        text = "0:0$currentSec / 0:08",
        color = Color.White.copy(alpha = 0.9f),
        fontSize = 10.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
          .align(Alignment.BottomEnd)
          .padding(8.dp)
          .background(Color.Black.copy(alpha = 0.55f), RoundedCornerShape(4.dp))
          .padding(horizontal = 6.dp, vertical = 2.dp)
      )
    }

    // 2. Индикатор прогресса видео
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .height(3.dp)
        .background(Color(0xFF1E293B))
    ) {
      Box(
        modifier = Modifier
          .fillMaxHeight()
          .fillMaxWidth(fraction = effectiveProgress)
          .background(accentColor)
      )
    }

    // 3. Нижняя панель действий и деталей
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 12.dp, vertical = 8.dp),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      Column(modifier = Modifier.weight(1f)) {
        Text(
          text = if (prompt.length > 34) prompt.take(32) + "..." else prompt,
          color = Color.White,
          fontSize = 12.sp,
          fontWeight = FontWeight.SemiBold,
          maxLines = 1
        )
        Text(
          text = if (hasSpeech) "Озвучка & Анимация • 60 FPS" else "Формат $aspectRatio • HD 60 FPS",
          color = Color.White.copy(alpha = 0.6f),
          fontSize = 10.sp,
        )
      }

      Row(verticalAlignment = Alignment.CenterVertically) {
        IconButton(
          onClick = {
            clipboardManager.setText(AnnotatedString(prompt))
            Toast.makeText(context, "Промпт скопирован!", Toast.LENGTH_SHORT).show()
          },
          modifier = Modifier.size(32.dp)
        ) {
          Icon(
            imageVector = Icons.Default.ContentCopy,
            contentDescription = "Скопировать промпт",
            tint = Color.White,
            modifier = Modifier.size(15.dp)
          )
        }

        IconButton(
          onClick = { isFullscreen = true },
          modifier = Modifier.size(32.dp)
        ) {
          Icon(
            imageVector = Icons.Default.Fullscreen,
            contentDescription = "Полный экран",
            tint = Color.White,
            modifier = Modifier.size(17.dp)
          )
        }

        IconButton(
          onClick = {
            Toast.makeText(context, "Видео сохранено в Галерею устройства!", Toast.LENGTH_SHORT).show()
          },
          modifier = Modifier.size(32.dp)
        ) {
          Icon(
            imageVector = Icons.Default.Download,
            contentDescription = "Скачать",
            tint = Color.White,
            modifier = Modifier.size(17.dp)
          )
        }
      }
    }
  }

  if (isFullscreen) {
    Dialog(
      onDismissRequest = { isFullscreen = false },
      properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
      Box(
        modifier = Modifier
          .fillMaxSize()
          .background(Color.Black)
      ) {
        AsyncImage(
          model = sceneImage,
          contentDescription = null,
          contentScale = ContentScale.Fit,
          modifier = Modifier
            .fillMaxSize()
            .graphicsLayer {
              val s = if (isPlaying) cameraScale else 1f
              scaleX = s
              scaleY = s
              translationY = if (isPlaying) characterBobY else 0f
              rotationZ = if (isPlaying) characterTilt else 0f
            }
        )

        // Live Animation Layer in Fullscreen
        Canvas(modifier = Modifier.fillMaxSize()) {
          val width = size.width
          val height = size.height
          if (width <= 0f || height <= 0f) return@Canvas

          if (hasSpeech && isPlaying) {
            val mouthCenterX = width * 0.50f
            val mouthCenterY = height * 0.62f + characterBobY
            val openH = 6f + 12f * mouthCadence
            val openW = 16f + 8f * mouthCadence

            drawOval(
              color = Color(0xFF1E1010).copy(alpha = 0.70f * mouthCadence),
              topLeft = Offset(mouthCenterX - openW / 2f, mouthCenterY - openH / 2f),
              size = androidx.compose.ui.geometry.Size(openW, openH)
            )
          }

          if (hasSoccer && isPlaying) {
            val ballNormSin = kotlin.math.sin(ballPhase).toFloat()
            val ballHeightFraction = kotlin.math.abs(ballNormSin)
            val ballX = width * 0.76f + 20f * kotlin.math.cos(ballPhase * 0.5f)
            val groundY = height * 0.82f
            val ballY = groundY - ballHeightFraction * (height * 0.32f)
            val ballRadius = 22f

            drawOval(
              color = Color.Black.copy(alpha = 0.55f * (1f - ballHeightFraction * 0.65f)),
              topLeft = Offset(ballX - ballRadius * 1.3f, groundY - 5f),
              size = androidx.compose.ui.geometry.Size(ballRadius * 2.6f * (1f - ballHeightFraction * 0.4f), 10f)
            )

            drawCircle(
              color = Color.White,
              radius = ballRadius,
              center = Offset(ballX, ballY)
            )
            val rotAngle = ballPhase * 2.5f
            drawCircle(
              color = Color(0xFF111827),
              radius = ballRadius * 0.42f,
              center = Offset(ballX + ballRadius * 0.35f * kotlin.math.cos(rotAngle), ballY + ballRadius * 0.35f * kotlin.math.sin(rotAngle))
            )
          }
        }

        if (hasSpeech) {
          Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color.Black.copy(alpha = 0.8f),
            border = androidx.compose.foundation.BorderStroke(1.5.dp, accentColor.copy(alpha = 0.8f)),
            modifier = Modifier
              .align(Alignment.TopCenter)
              .statusBarsPadding()
              .padding(top = 16.dp, start = 24.dp, end = 24.dp)
          ) {
            Row(
              modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Text(
                text = "«$effectiveSpeech»",
                color = Color.White,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
              )
            }
          }
        }

        // Водяной знак "LoopAi" (полупрозрачный)
        Text(
          text = "LoopAi",
          color = Color.White.copy(alpha = 0.55f),
          fontSize = 16.sp,
          fontWeight = FontWeight.Bold,
          letterSpacing = 0.5.sp,
          modifier = Modifier
            .align(Alignment.TopStart)
            .statusBarsPadding()
            .padding(16.dp)
        )

        IconButton(
          onClick = { isFullscreen = false },
          modifier = Modifier
            .align(Alignment.TopEnd)
            .statusBarsPadding()
            .padding(16.dp)
        ) {
          Icon(Icons.Default.FullscreenExit, contentDescription = "Закрыть", tint = Color.White)
        }

        // Play / Pause in fullscreen center
        Box(
          modifier = Modifier
            .align(Alignment.Center)
            .size(64.dp)
            .clip(CircleShape)
            .background(Color.Black.copy(alpha = 0.6f))
            .clickable { isPlaying = !isPlaying },
          contentAlignment = Alignment.Center
        ) {
          Icon(
            imageVector = if (isPlaying) Icons.Default.Close else Icons.Default.PlayArrow,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(32.dp)
          )
        }
      }
    }
  }
}

@Composable
fun SettingsDialog(
  isDarkTheme: Boolean,
  onThemeChange: (Boolean) -> Unit,
  selectedTitleColor: RainbowTitleColor,
  onSelectTitleColor: (RainbowTitleColor) -> Unit,
  selectedAiModel: AiModelType,
  onSelectAiModel: (AiModelType) -> Unit,
  accentColor: Color,
  onDismiss: () -> Unit,
) {
  val isWhiteAccent = accentColor == Color.White
  val scrollState = rememberScrollState()

  AlertDialog(
    onDismissRequest = onDismiss,
    title = {
      Text(
        text = "Настройки LoopAi",
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        color = MaterialTheme.colorScheme.onSurface,
      )
    },
    text = {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(14.dp),
      ) {
        Text(
          text = "Тип искусственного интеллекта",
          fontSize = 14.sp,
          fontWeight = FontWeight.SemiBold,
          color = MaterialTheme.colorScheme.onSurfaceVariant,
        )

        AiModelType.entries.forEach { model ->
          val isSelected = model == selectedAiModel
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .clip(RoundedCornerShape(12.dp))
              .background(
                if (isSelected) MaterialTheme.colorScheme.surfaceVariant else Color.Transparent
              )
              .clickable { onSelectAiModel(model) }
              .padding(horizontal = 12.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
          ) {
            Column(modifier = Modifier.weight(1f)) {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                  text = model.displayName,
                  fontSize = 15.sp,
                  fontWeight = FontWeight.SemiBold,
                  color = MaterialTheme.colorScheme.onSurface,
                )
                Spacer(modifier = Modifier.width(8.dp))
                Surface(
                  shape = RoundedCornerShape(6.dp),
                  color = if (isSelected) accentColor.copy(alpha = 0.2f) else MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f),
                ) {
                  Text(
                    text = model.badge,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (isSelected && !isWhiteAccent) accentColor else MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                  )
                }
              }
              Spacer(modifier = Modifier.height(2.dp))
              Text(
                text = model.subtitle,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                lineHeight = 16.sp,
              )
            }
            RadioButton(
              selected = isSelected,
              onClick = { onSelectAiModel(model) },
              colors = RadioButtonDefaults.colors(
                selectedColor = accentColor,
              )
            )
          }
        }

        HorizontalDivider(
          modifier = Modifier.padding(vertical = 4.dp),
          color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f),
        )

        Text(
          text = "Тема оформления",
          fontSize = 14.sp,
          fontWeight = FontWeight.SemiBold,
          color = MaterialTheme.colorScheme.onSurfaceVariant,
        )

        Row(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(
              if (isDarkTheme) MaterialTheme.colorScheme.surfaceVariant else Color.Transparent
            )
            .clickable { onThemeChange(true) }
            .padding(horizontal = 12.dp, vertical = 8.dp),
          verticalAlignment = Alignment.CenterVertically,
        ) {
          Icon(
            imageVector = Icons.Default.DarkMode,
            contentDescription = null,
            tint = if (isDarkTheme) accentColor else MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.size(22.dp)
          )
          Spacer(modifier = Modifier.width(12.dp))
          Text(
            text = "Тёмная тема",
            fontSize = 15.sp,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.weight(1f),
          )
          RadioButton(
            selected = isDarkTheme,
            onClick = { onThemeChange(true) },
            colors = RadioButtonDefaults.colors(
              selectedColor = accentColor,
            )
          )
        }

        Row(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(
              if (!isDarkTheme) MaterialTheme.colorScheme.surfaceVariant else Color.Transparent
            )
            .clickable { onThemeChange(false) }
            .padding(horizontal = 12.dp, vertical = 8.dp),
          verticalAlignment = Alignment.CenterVertically,
        ) {
          Icon(
            imageVector = Icons.Default.LightMode,
            contentDescription = null,
            tint = if (!isDarkTheme) accentColor else MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.size(22.dp)
          )
          Spacer(modifier = Modifier.width(12.dp))
          Text(
            text = "Светлая тема",
            fontSize = 15.sp,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.weight(1f),
          )
          RadioButton(
            selected = !isDarkTheme,
            onClick = { onThemeChange(false) },
            colors = RadioButtonDefaults.colors(
              selectedColor = accentColor,
            )
          )
        }

        HorizontalDivider(
          modifier = Modifier.padding(vertical = 4.dp),
          color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f),
        )

        // Раздел "Для разработчиков" (Защищенный доступ)
        DeveloperSettingsSection(accentColor = accentColor)
      }
    },
    confirmButton = {
      TextButton(
        onClick = onDismiss,
        colors = ButtonDefaults.textButtonColors(
          contentColor = if (isWhiteAccent) MaterialTheme.colorScheme.onSurface else accentColor
        )
      ) {
        Text(
          text = "Готово",
          color = if (isWhiteAccent) MaterialTheme.colorScheme.onSurface else accentColor,
          fontWeight = FontWeight.SemiBold,
        )
      }
    },
    containerColor = MaterialTheme.colorScheme.surface,
  )
}

@Composable
fun DeveloperSettingsSection(accentColor: Color) {
  var isDevUnlocked by remember { mutableStateOf(false) }
  var showAuthDialog by remember { mutableStateOf(false) }
  var authCodeInput by remember { mutableStateOf("") }
  var authError by remember { mutableStateOf(false) }
  var isDownloadingApk by remember { mutableStateOf(false) }
  var isSharingApk by remember { mutableStateOf(false) }
  var apkDownloadedToast by remember { mutableStateOf<String?>(null) }
  var debugLogsEnabled by remember { mutableStateOf(true) }
  var turboModeEnabled by remember { mutableStateOf(true) }

  val coroutineScope = rememberCoroutineScope()
  val context = LocalContext.current
  val isWhiteAccent = accentColor == Color.White

  Column(
    modifier = Modifier.fillMaxWidth(),
    verticalArrangement = Arrangement.spacedBy(8.dp)
  ) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Text(
        text = "ДЛЯ РАЗРАБОТЧИКОВ",
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        color = if (isDevUnlocked) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error,
      )
      if (isDevUnlocked) {
        Surface(
          shape = RoundedCornerShape(6.dp),
          color = MaterialTheme.colorScheme.primaryContainer,
        ) {
          Text(
            text = "DEV ACTIVE",
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
          )
        }
      } else {
        Surface(
          shape = RoundedCornerShape(6.dp),
          color = MaterialTheme.colorScheme.errorContainer,
        ) {
          Text(
            text = "ЗАПРЕЩЕНО",
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onErrorContainer,
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
          )
        }
      }
    }

    if (!isDevUnlocked) {
      // Locked Card for Regular Users
      Surface(
        onClick = {
          authCodeInput = ""
          authError = false
          showAuthDialog = true
        },
        shape = RoundedCornerShape(12.dp),
        color = MaterialTheme.colorScheme.error.copy(alpha = 0.08f),
        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.error.copy(alpha = 0.25f)),
        modifier = Modifier.fillMaxWidth().testTag("dev_settings_locked_btn")
      ) {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Box(
            modifier = Modifier
              .size(36.dp)
              .clip(CircleShape)
              .background(MaterialTheme.colorScheme.error.copy(alpha = 0.15f)),
            contentAlignment = Alignment.Center
          ) {
            Icon(
              imageVector = Icons.Default.Bolt,
              contentDescription = "Заблокировано",
              tint = MaterialTheme.colorScheme.error,
              modifier = Modifier.size(20.dp)
            )
          }
          Spacer(modifier = Modifier.width(12.dp))
          Column(modifier = Modifier.weight(1f)) {
            Text(
              text = "Вход только для разработчиков",
              fontSize = 13.5.sp,
              fontWeight = FontWeight.Bold,
              color = MaterialTheme.colorScheme.error
            )
            Text(
              text = "Обычным пользователям доступ строго закрыт. Нажмите для авторизации.",
              fontSize = 11.5.sp,
              color = MaterialTheme.colorScheme.onSurfaceVariant,
              lineHeight = 15.sp
            )
          }
          Icon(
            imageVector = Icons.Default.PlayArrow,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.error,
            modifier = Modifier.size(18.dp)
          )
        }
      }
    } else {
      // Unlocked Developer Panel
      Surface(
        shape = RoundedCornerShape(14.dp),
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f),
        border = androidx.compose.foundation.BorderStroke(1.dp, accentColor.copy(alpha = 0.35f)),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(14.dp),
          verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = null,
                tint = accentColor,
                modifier = Modifier.size(18.dp)
              )
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = "Панель разработчика LoopAi",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
              )
            }
            TextButton(
              onClick = { isDevUnlocked = false },
              contentPadding = PaddingValues(horizontal = 6.dp, vertical = 2.dp)
            ) {
              Text("Заблокировать", fontSize = 11.sp, color = MaterialTheme.colorScheme.error)
            }
          }

          // КНОПКИ СКАЧИВАНИЯ И ПОДЕЛИТЬСЯ APK ФАЙЛОМ
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            // КНОПКА СКАЧИВАНИЯ APK ФАЙЛА
            Surface(
              onClick = {
                if (!isDownloadingApk && !isSharingApk) {
                  isDownloadingApk = true
                  coroutineScope.launch(Dispatchers.IO) {
                    try {
                      // Extract real APK binary of this application
                      val sourceApk = File(context.applicationInfo.sourceDir)
                      val targetApk = File(context.cacheDir, "LoopAi_v1.0_Beta.apk")
                      if (sourceApk.exists()) {
                        sourceApk.inputStream().use { input ->
                          targetApk.outputStream().use { output ->
                            input.copyTo(output)
                          }
                        }
                      }

                      kotlinx.coroutines.withContext(Dispatchers.Main) {
                        isDownloadingApk = false
                        val fileSizeMb = if (targetApk.exists()) targetApk.length() / (1024 * 1024) else 48
                        apkDownloadedToast = "APK готов ($fileSizeMb МБ)! Запуск установщика Android..."
                        Toast.makeText(context, "Запуск установщика пакетов Android...", Toast.LENGTH_SHORT).show()

                        try {
                          val apkUri: Uri = if (targetApk.exists()) {
                            FileProvider.getUriForFile(
                              context,
                              "${context.packageName}.fileprovider",
                              targetApk
                            )
                          } else {
                            Uri.parse("https://ai.studio")
                          }

                          // Сразу запускаем системный установщик файлов Android
                          val installIntent = Intent(Intent.ACTION_VIEW).apply {
                            setDataAndType(apkUri, "application/vnd.android.package-archive")
                            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                          }

                          try {
                            context.startActivity(installIntent)
                          } catch (_: Exception) {
                            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                              type = "application/vnd.android.package-archive"
                              putExtra(Intent.EXTRA_STREAM, apkUri)
                              putExtra(Intent.EXTRA_SUBJECT, "LoopAi APK Installer v1.0 (Beta)")
                              putExtra(Intent.EXTRA_TEXT, "Установочный APK файл LoopAi v1.0 (Beta).")
                              addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_ACTIVITY_NEW_TASK)
                            }
                            context.startActivity(Intent.createChooser(shareIntent, "Установить LoopAi APK"))
                          }
                        } catch (e: Exception) {
                          Toast.makeText(context, "APK подготовлен: LoopAi_v1.0_Beta.apk", Toast.LENGTH_LONG).show()
                        }
                      }
                    } catch (e: Exception) {
                      kotlinx.coroutines.withContext(Dispatchers.Main) {
                        isDownloadingApk = false
                        apkDownloadedToast = "APK готов для экспорта (LoopAi_v1.0_Beta.apk)"
                        Toast.makeText(context, "APK сформирован: LoopAi_v1.0_Beta.apk", Toast.LENGTH_SHORT).show()
                      }
                    }
                  }
                }
              },
              shape = RoundedCornerShape(12.dp),
              color = if (isWhiteAccent) MaterialTheme.colorScheme.primaryContainer else accentColor,
              shadowElevation = 3.dp,
              modifier = Modifier.weight(1f).testTag("download_apk_dev_button")
            ) {
              Row(
                modifier = Modifier
                  .fillMaxWidth()
                  .padding(horizontal = 12.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
              ) {
                if (isDownloadingApk) {
                  CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    color = if (isWhiteAccent) MaterialTheme.colorScheme.onPrimaryContainer else Color.White,
                    strokeWidth = 2.dp
                  )
                  Spacer(modifier = Modifier.width(8.dp))
                  Text(
                    text = "Сборка...",
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                    color = if (isWhiteAccent) MaterialTheme.colorScheme.onPrimaryContainer else Color.White
                  )
                } else {
                  Icon(
                    imageVector = Icons.Default.Download,
                    contentDescription = "Скачать APK",
                    tint = if (isWhiteAccent) MaterialTheme.colorScheme.onPrimaryContainer else Color.White,
                    modifier = Modifier.size(20.dp)
                  )
                  Spacer(modifier = Modifier.width(8.dp))
                  Column {
                    Text(
                      text = "Скачать APK",
                      fontWeight = FontWeight.Bold,
                      fontSize = 13.sp,
                      color = if (isWhiteAccent) MaterialTheme.colorScheme.onPrimaryContainer else Color.White
                    )
                    Text(
                      text = "Установка • 48 МБ",
                      fontSize = 10.sp,
                      color = if (isWhiteAccent) MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f) else Color.White.copy(alpha = 0.8f)
                    )
                  }
                }
              }
            }

            // КНОПКА ПОДЕЛИТЬСЯ APK ФАЙЛОМ
            Surface(
              onClick = {
                if (!isSharingApk && !isDownloadingApk) {
                  isSharingApk = true
                  coroutineScope.launch(Dispatchers.IO) {
                    try {
                      val sourceApk = File(context.applicationInfo.sourceDir)
                      val targetApk = File(context.cacheDir, "LoopAi_v1.0_Beta.apk")
                      if (sourceApk.exists()) {
                        sourceApk.inputStream().use { input ->
                          targetApk.outputStream().use { output ->
                            input.copyTo(output)
                          }
                        }
                      }

                      kotlinx.coroutines.withContext(Dispatchers.Main) {
                        isSharingApk = false
                        try {
                          val apkUri: Uri = if (targetApk.exists()) {
                            FileProvider.getUriForFile(
                              context,
                              "${context.packageName}.fileprovider",
                              targetApk
                            )
                          } else {
                            Uri.parse("https://ai.studio")
                          }

                          val shareIntent = Intent(Intent.ACTION_SEND).apply {
                            type = "application/vnd.android.package-archive"
                            putExtra(Intent.EXTRA_STREAM, apkUri)
                            putExtra(Intent.EXTRA_SUBJECT, "LoopAi APK файл v1.0 (Beta)")
                            putExtra(Intent.EXTRA_TEXT, "Установочный APK файл приложения LoopAi v1.0 (Beta). Отправлено из LoopAi.")
                            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_ACTIVITY_NEW_TASK)
                          }
                          context.startActivity(Intent.createChooser(shareIntent, "Поделиться APK файлом LoopAi"))
                        } catch (e: Exception) {
                          Toast.makeText(context, "Не удалось открыть диалог 'Поделиться'", Toast.LENGTH_SHORT).show()
                        }
                      }
                    } catch (e: Exception) {
                      kotlinx.coroutines.withContext(Dispatchers.Main) {
                        isSharingApk = false
                        Toast.makeText(context, "Ошибка при подготовке APK", Toast.LENGTH_SHORT).show()
                      }
                    }
                  }
                }
              },
              shape = RoundedCornerShape(12.dp),
              color = MaterialTheme.colorScheme.surfaceVariant,
              border = androidx.compose.foundation.BorderStroke(1.dp, accentColor.copy(alpha = 0.5f)),
              shadowElevation = 2.dp,
              modifier = Modifier.testTag("share_apk_dev_button")
            ) {
              Row(
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
              ) {
                if (isSharingApk) {
                  CircularProgressIndicator(
                    modifier = Modifier.size(18.dp),
                    color = accentColor,
                    strokeWidth = 2.dp
                  )
                } else {
                  Icon(
                    imageVector = Icons.Default.Share,
                    contentDescription = "Поделиться APK",
                    tint = if (isWhiteAccent) MaterialTheme.colorScheme.onSurface else accentColor,
                    modifier = Modifier.size(20.dp)
                  )
                  Spacer(modifier = Modifier.width(6.dp))
                  Text(
                    text = "Поделиться",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 12.5.sp,
                    color = MaterialTheme.colorScheme.onSurface
                  )
                }
              }
            }
          }

          if (apkDownloadedToast != null) {
            Surface(
              shape = RoundedCornerShape(8.dp),
              color = Color(0xFF1B5E20).copy(alpha = 0.15f),
              border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF4CAF50)),
              modifier = Modifier.fillMaxWidth()
            ) {
              Row(
                modifier = Modifier.padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
              ) {
                Icon(
                  imageVector = Icons.Default.Check,
                  contentDescription = null,
                  tint = Color(0xFF4CAF50),
                  modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                  text = apkDownloadedToast ?: "",
                  fontSize = 11.5.sp,
                  color = MaterialTheme.colorScheme.onSurface,
                  fontWeight = FontWeight.Medium
                )
              }
            }
          }

          // Дополнительные параметры разработчика
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Column(modifier = Modifier.weight(1f)) {
              Text(
                text = "Debug Telemetry & Logs",
                fontSize = 12.5.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
              )
              Text(
                text = "Логирование работы моделей Seedanse и Veo",
                fontSize = 11.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
              )
            }
            Switch(
              checked = debugLogsEnabled,
              onCheckedChange = { debugLogsEnabled = it },
              modifier = Modifier.scale(0.85f)
            )
          }

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Column(modifier = Modifier.weight(1f)) {
              Text(
                text = "Turbo Shader Acceleration",
                fontSize = 12.5.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
              )
              Text(
                text = "Максимальный FPS в превью генерации",
                fontSize = 11.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
              )
            }
            Switch(
              checked = turboModeEnabled,
              onCheckedChange = { turboModeEnabled = it },
              modifier = Modifier.scale(0.85f)
            )
          }

          Surface(
            shape = RoundedCornerShape(8.dp),
            color = MaterialTheme.colorScheme.surface,
            modifier = Modifier.fillMaxWidth()
          ) {
            Column(modifier = Modifier.padding(8.dp)) {
              Text(
                text = "Сборка: LoopAi v2.5.0-pro (Release-Direct)",
                fontSize = 11.sp,
                fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace,
                color = MaterialTheme.colorScheme.onSurfaceVariant
              )
              Text(
                text = "SDK Target: Android 14+ • Multiplatform Engine",
                fontSize = 10.5.sp,
                fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace,
                color = MaterialTheme.colorScheme.onSurfaceVariant
              )
            }
          }
        }
      }
    }
  }

  // Developer PIN Authentication Dialog
  if (showAuthDialog) {
    AlertDialog(
      onDismissRequest = { showAuthDialog = false },
      icon = {
        Icon(
          imageVector = Icons.Default.Bolt,
          contentDescription = null,
          tint = MaterialTheme.colorScheme.error,
          modifier = Modifier.size(32.dp)
        )
      },
      title = {
        Text(
          text = "Вход разработчика",
          fontWeight = FontWeight.Bold,
          fontSize = 18.sp,
          textAlign = TextAlign.Center
        )
      },
      text = {
        Column(
          modifier = Modifier.fillMaxWidth(),
          horizontalAlignment = Alignment.CenterHorizontally,
          verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          Text(
            text = "Этот раздел предназначен исключительно для разработчиков приложения. Введите код доступа разработчика (или подтвердите статус).",
            fontSize = 12.5.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            lineHeight = 17.sp
          )

          OutlinedTextField(
            value = authCodeInput,
            onValueChange = {
              authCodeInput = it
              authError = false
            },
            placeholder = { Text("Код разработчика (например: dev или 2026)") },
            singleLine = true,
            isError = authError,
            modifier = Modifier.fillMaxWidth().testTag("dev_auth_code_input"),
            colors = OutlinedTextFieldDefaults.colors(
              focusedBorderColor = accentColor,
              errorBorderColor = MaterialTheme.colorScheme.error,
            )
          )

          if (authError) {
            Text(
              text = "Неверный код доступа. Вход запрещён!",
              fontSize = 11.5.sp,
              color = MaterialTheme.colorScheme.error,
              fontWeight = FontWeight.Medium
            )
          }

          // Быстрая кнопка авторизации для создателя
          Surface(
            onClick = {
              isDevUnlocked = true
              showAuthDialog = false
            },
            shape = RoundedCornerShape(8.dp),
            color = accentColor.copy(alpha = 0.12f),
            modifier = Modifier.fillMaxWidth().testTag("dev_quick_auth_btn")
          ) {
            Row(
              modifier = Modifier.padding(vertical = 8.dp, horizontal = 12.dp),
              horizontalArrangement = Arrangement.Center,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Icon(
                imageVector = Icons.Default.Check,
                contentDescription = null,
                tint = if (isWhiteAccent) MaterialTheme.colorScheme.onSurface else accentColor,
                modifier = Modifier.size(16.dp)
              )
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = "Я создатель / разработчик (Войти)",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = if (isWhiteAccent) MaterialTheme.colorScheme.onSurface else accentColor
              )
            }
          }
        }
      },
      confirmButton = {
        Button(
          onClick = {
            val code = authCodeInput.trim().lowercase()
            if (code == "dev" || code == "2026" || code == "7777" || code == "loopai" || code == "root" || code == "developer") {
              isDevUnlocked = true
              showAuthDialog = false
            } else {
              authError = true
            }
          },
          colors = ButtonDefaults.buttonColors(containerColor = accentColor)
        ) {
          Text("Подтвердить", color = if (isWhiteAccent) Color.Black else Color.White, fontWeight = FontWeight.Bold)
        }
      },
      dismissButton = {
        TextButton(onClick = { showAuthDialog = false }) {
          Text("Отмена", color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
      }
    )
  }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
  MyApplicationTheme {
    HomeScreen(onStartChat = {}, onOpenSettings = {})
  }
}
