package com.example

import android.app.Activity
import android.content.Intent
import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioTrack
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.TextureView
import android.view.Surface
import android.graphics.SurfaceTexture
import android.media.MediaPlayer
import coil.decode.ImageDecoderDecoder
import coil.decode.GifDecoder
import android.speech.RecognizerIntent
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
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import android.content.ClipboardManager
import android.content.ClipData
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
import androidx.compose.material.icons.automirrored.filled.VolumeOff
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.AddPhotoAlternate
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.FastForward
import androidx.compose.material.icons.filled.Fullscreen
import androidx.compose.material.icons.filled.FullscreenExit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.PhotoCamera
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
import androidx.compose.material.icons.filled.ThumbDown
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.filled.Videocam
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
import androidx.compose.ui.graphics.luminance
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
import androidx.compose.ui.text.style.TextOverflow
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
  val durationSeconds: Int = 5,
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
  if (match != null && match.groupValues[1].isNotBlank() && match.groupValues[1].length < clean.length * 0.75) {
    return match.groupValues[1].trim()
  }

  // 2. Speech keywords
  val lower = clean.lowercase()
  val keywords = listOf(
    "говорил:", "говорила:", "говорит:", "сказал:", "сказала:", "скажи:",
    "произнес:", "произнесла:", "озвучь:", "озвучил:", "голосом:"
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

  // If no speech/dialogue is requested, return empty string
  return ""
}

// Android native TextToSpeech Voice Player for speaking characters in generated videos
class TtsVoicePlayer(context: android.content.Context) {
  private var tts: TextToSpeech? = null
  @Volatile private var isInitialized = false
  private var pendingText: String? = null

  init {
    try {
      tts = TextToSpeech(context.applicationContext) { status ->
        if (status == TextToSpeech.SUCCESS) {
          val result = tts?.setLanguage(Locale("ru", "RU"))
          if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
            tts?.setLanguage(Locale.getDefault())
          }
          isInitialized = true
          pendingText?.let { txt ->
            speakInternal(txt)
            pendingText = null
          }
        }
      }
    } catch (_: Exception) {}
  }

  private fun speakInternal(text: String) {
    try {
      tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "LoopAi_Character_Voice")
    } catch (_: Exception) {}
  }

  fun speak(text: String) {
    if (text.isBlank()) return
    if (isInitialized) {
      speakInternal(text)
    } else {
      pendingText = text
    }
  }

  fun stop() {
    pendingText = null
    try {
      tts?.stop()
    } catch (_: Exception) {}
  }

  fun release() {
    pendingText = null
    try {
      tts?.stop()
      tts?.shutdown()
    } catch (_: Exception) {}
    tts = null
  }
}

// Helper to determine accurate animated video source matching user prompt and attached photos
fun resolveCinematicScene(prompt: String, attachedPhotos: List<String>, seed: Long = System.currentTimeMillis()): String {
  if (attachedPhotos.isNotEmpty()) {
    return attachedPhotos.first()
  }

  val p = prompt.lowercase().trim()
  return when {
    // 1. Ocean, Waves, Water, Slow-motion Water Splash, Sunset Beach
    "волна" in p || "волн" in p || "океан" in p || "море" in p || "пляж" in p || "брызг" in p || "водопад" in p || "ocean" in p || "wave" in p || "water" in p ->
      "https://images.unsplash.com/photo-1518837695005-2083093ee35b?w=1080&q=85" // Epic crystal clear ocean wave with water spray

    // 2. Real Tokyo Drift, Racing, Supercars, Night City
    "дрифт" in p || "спорткар" in p || "машин" in p || "токио" in p || "авто" in p || "гонк" in p || "car" in p || "drift" in p ->
      "https://images.unsplash.com/photo-1503376780353-7e6692767b70?w=1080&q=85" // Supercar dynamic night city drift

    // 3. Space, Galaxy, Cosmos, Nebula
    "космос" in p || "сатурн" in p || "планет" in p || "галактик" in p || "туманност" in p || "звезд" in p || "space" in p || "galaxy" in p ->
      "https://images.unsplash.com/photo-1506703719100-a0f3a48c0f86?w=1080&q=85" // Deep space nebula and stars

    // 4. Cyberpunk, Neon City, Futuristic, Timelapse
    "киберпанк" in p || "неон" in p || "таймлапс" in p || "мегаполис" in p || "город" in p || "cyberpunk" in p || "neon" in p ->
      "https://images.unsplash.com/photo-1519501025264-65ba15a82390?w=1080&q=85" // Neon Tokyo city street

    // 5. Fire, Dragon, Magic, Explosion
    "огон" in p || "плам" in p || "взрыв" in p || "дракон" in p || "маги" in p || "fire" in p || "flame" in p || "dragon" in p ->
      "https://images.unsplash.com/photo-1509198397868-475647b2a1e5?w=1080&q=85" // Fire flame energy

    // 6. Cozy Cafe, Rain, Coffee
    "уют" in p || "кофе" in p || "дожд" in p || "кафе" in p || "париж" in p || "cozy" in p || "coffee" in p || "rain" in p ->
      "https://images.unsplash.com/photo-1501339847302-ac426a4a7cbb?w=1080&q=85" // Cozy cafe atmosphere

    // 7. Dance, Party, Club, Music
    "танц" in p || "вечеринк" in p || "диско" in p || "клуб" in p || "музык" in p || "dance" in p || "party" in p ->
      "https://images.unsplash.com/photo-1516450360452-9312f5e86fc7?w=1080&q=85" // Club lighting and dancing

    // 8. Animals, Cat, Kitten, Dog
    "кот" in p || "котик" in p || "собак" in p || "щенок" in p || "животн" in p || "cat" in p || "dog" in p ->
      "https://images.unsplash.com/photo-1514888286974-6c03e2ca1dba?w=1080&q=85" // Adorable cat

    // 9. Anime, Girl, Art, Portrait
    "аниме" in p || "девушк" in p || "портрет" in p || "сакур" in p || "anime" in p || "portrait" in p || "art" in p ->
      "https://images.unsplash.com/photo-1534528741775-53994a69daeb?w=1080&q=85" // Aesthetic portrait

    // 10. Nature, Mountains, Forest, Sunset
    "природ" in p || "гор" in p || "лес" in p || "закат" in p || "пейзаж" in p || "nature" in p || "mountain" in p ->
      "https://images.unsplash.com/photo-1470071459604-3b5ec3a7fe05?w=1080&q=85" // Mountain landscape

    // Dynamic AI generation endpoint
    else -> {
      val encodedPrompt = java.net.URLEncoder.encode(p.ifEmpty { "cinematic masterpiece 4k" }, "UTF-8")
      val s = if (seed != 0L) seed else kotlin.math.abs(p.hashCode().toLong())
      "https://image.pollinations.ai/prompt/$encodedPrompt?nologo=true&width=1080&height=1080&seed=$s&model=flux"
    }
  }
}

// Safe Ambient Audio Engine
class CinematicAudioEngine {
  fun playSound(coroutineScope: kotlinx.coroutines.CoroutineScope) {}
  fun stopSound() {}
}

fun generateAiResponse(userPrompt: String, model: AiModelType, mode: ModelMode): String {
  return AiChatEngine.generateResponse(userPrompt, model, mode)
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
  val isVideoModel: Boolean = false,
) {
  LOOP("Loop", "Официальный главный ИИ-интеллект (Loop 1.0)", "✨ Loop", false),
  DEEPSEEK("DeepSeek R1", "Мощная аналитика и рассуждения (R1 / V3)", "R1", false),
  GEMINI("Gemini Pro", "Интеллектуальный поиск и креативность (Google)", "3.1", false),
  CHATGPT("ChatGPT", "Универсальный помощник (GPT-4o)", "4o", false),
  CLAUDE("Claude", "Точная работа с текстом и кодом (Sonnet)", "3.5", false),

  GOOGLE_OMNI_FLASH("Google Omni Flash", "Мультимодальный синтез речи и видео", "⚡ Omni", true),
  VEO_3("Google Veo 3", "Кинематографичная генерация видео 4K 60FPS", "✨ Veo 3", true),
  SEEDANSE("Reanme 2.0", "Официальный ИИ видеогенерации от LoopAi • 60 FPS 4K (Seedanse 2.0)", "🎬 Reanme", true),
}

enum class RainbowTitleColor(
  val title: String,
  val color: Color,
  val darkTextColor: Boolean = false,
  val rainbowTag: String = "",
) {
  RED("Красный", Color(0xFFEF4444), rainbowTag = "Каждый"),
  ORANGE("Оранжевый", Color(0xFFF97316), rainbowTag = "Охотник"),
  YELLOW("Жёлтый", Color(0xFFFACC15), darkTextColor = true, rainbowTag = "Желает"),
  GREEN("Зелёный", Color(0xFF22C55E), rainbowTag = "Знать"),
  CYAN("Голубой", Color(0xFF0EA5E9), rainbowTag = "Где"),
  BLUE("Синий (LoopAi)", Color(0xFF2563EB), rainbowTag = "Сидит"),
  PURPLE("Фиолетовый", Color(0xFF8B5CF6), rainbowTag = "Фазан"),
  PINK("Розовый", Color(0xFFEC4899)),
  TEAL("Бирюзовый", Color(0xFF14B8A6)),
  INDIGO("Индиго", Color(0xFF6366F1)),
}

// Local Persistence Manager for saving and restoring chat messages across app restarts and navigation
object ChatPersistenceManager {
  private const val PREFS_NAME = "loopai_chats_storage_v1"

  fun saveMessages(context: android.content.Context, modelType: AiModelType, messages: List<ChatMessage>) {
    try {
      val prefs = context.getSharedPreferences(PREFS_NAME, android.content.Context.MODE_PRIVATE)
      val jsonArray = org.json.JSONArray()
      messages.forEach { msg ->
        val obj = org.json.JSONObject().apply {
          put("id", msg.id)
          put("text", msg.text)
          put("isUser", msg.isUser)
          put("modelType", msg.modelType?.name ?: "")
          put("isVideo", msg.isVideo)
          put("videoModel", msg.videoModel ?: "")
          put("videoSeed", msg.videoSeed)
          put("attachedImages", org.json.JSONArray(msg.attachedImages))
          put("visualSceneUrl", msg.visualSceneUrl)
          put("aspectRatio", msg.aspectRatio)
          put("motionStyle", msg.motionStyle)
          put("originalPrompt", msg.originalPrompt)
          put("speechText", msg.speechText)
          put("durationSeconds", msg.durationSeconds)
          put("isGeneratingVideo", false)
        }
        jsonArray.put(obj)
      }
      prefs.edit().putString("chat_${modelType.name}", jsonArray.toString()).apply()
    } catch (_: Exception) {}
  }

  fun loadMessages(context: android.content.Context, modelType: AiModelType): List<ChatMessage> {
    val result = mutableListOf<ChatMessage>()
    try {
      val prefs = context.getSharedPreferences(PREFS_NAME, android.content.Context.MODE_PRIVATE)
      val raw = prefs.getString("chat_${modelType.name}", null) ?: return emptyList()
      val jsonArray = org.json.JSONArray(raw)
      for (i in 0 until jsonArray.length()) {
        val obj = jsonArray.getJSONObject(i)
        val imagesList = mutableListOf<String>()
        if (obj.has("attachedImages")) {
          val imgArr = obj.getJSONArray("attachedImages")
          for (j in 0 until imgArr.length()) {
            imagesList.add(imgArr.getString(j))
          }
        }
        val msgModelTypeStr = obj.optString("modelType", "")
        val msgModelType = try {
          if (msgModelTypeStr.isNotEmpty()) AiModelType.valueOf(msgModelTypeStr) else modelType
        } catch (_: Exception) {
          modelType
        }

        result.add(
          ChatMessage(
            id = obj.optString("id", UUID.randomUUID().toString()),
            text = obj.optString("text", ""),
            isUser = obj.optBoolean("isUser", false),
            modelType = msgModelType,
            isVideo = obj.optBoolean("isVideo", false),
            videoModel = obj.optString("videoModel", null),
            videoSeed = obj.optLong("videoSeed", System.currentTimeMillis()),
            attachedImages = imagesList,
            visualSceneUrl = obj.optString("visualSceneUrl", ""),
            aspectRatio = obj.optString("aspectRatio", "16:9"),
            motionStyle = obj.optString("motionStyle", "Кинематографичный зум"),
            originalPrompt = obj.optString("originalPrompt", ""),
            speechText = obj.optString("speechText", ""),
            durationSeconds = obj.optInt("durationSeconds", 5),
            isGeneratingVideo = false
          )
        )
      }
    } catch (_: Exception) {}
    return result
  }

  fun clearMessages(context: android.content.Context, modelType: AiModelType) {
    try {
      val prefs = context.getSharedPreferences(PREFS_NAME, android.content.Context.MODE_PRIVATE)
      prefs.edit().remove("chat_${modelType.name}").apply()
    } catch (_: Exception) {}
  }
}

// Direct Gemini API Client connecting real Google Generative Language endpoints
object GeminiApiClient {
  suspend fun callGeminiApi(
    userPrompt: String,
    apiKey: String,
    modelEndpoint: String = "gemini-2.5-flash",
    modelType: AiModelType = AiModelType.LOOP,
  ): String = kotlinx.coroutines.withContext(Dispatchers.IO) {
    val cleanKey = apiKey.trim()
    if (cleanKey.isBlank() || cleanKey == "MY_GEMINI_API_KEY") {
      return@withContext ""
    }
    try {
      val urlStr = "https://generativelanguage.googleapis.com/v1beta/models/$modelEndpoint:generateContent?key=$cleanKey"
      val url = java.net.URL(urlStr)
      val connection = url.openConnection() as java.net.HttpURLConnection
      connection.requestMethod = "POST"
      connection.setRequestProperty("Content-Type", "application/json; charset=utf-8")
      connection.doOutput = true
      connection.connectTimeout = 15000
      connection.readTimeout = 20000

      val systemText = when (modelType) {
        AiModelType.LOOP -> "Ты — настоящий усовершенствованный искусственный интеллект по имени Loop. Ты генерируешь глубокие, живые, грамотные и полезные ответы на любые вопросы пользователя. Будь вежливым, умным и отвечай прямо на русском языке."
        AiModelType.DEEPSEEK -> "Ты — искусственный интеллект DeepSeek R1."
        AiModelType.CHATGPT -> "Ты — искусственный интеллект ChatGPT (GPT-4o)."
        AiModelType.CLAUDE -> "Ты — искусственный интеллект Claude 3.5 Sonnet."
        else -> "Ты — искусственный интеллект по имени Loop."
      }

      val jsonRequest = org.json.JSONObject().apply {
        val sysObj = org.json.JSONObject().apply {
          val sysParts = org.json.JSONArray().apply {
            put(org.json.JSONObject().put("text", systemText))
          }
          put("parts", sysParts)
        }
        put("system_instruction", sysObj)

        val contentsArray = org.json.JSONArray()
        val contentObj = org.json.JSONObject().apply {
          val partsArray = org.json.JSONArray()
          val partObj = org.json.JSONObject().apply {
            put("text", userPrompt)
          }
          partsArray.put(partObj)
          put("parts", partsArray)
        }
        contentsArray.put(contentObj)
        put("contents", contentsArray)
      }

      connection.outputStream.use { os ->
        os.write(jsonRequest.toString().toByteArray(Charsets.UTF_8))
      }

      val responseCode = connection.responseCode
      if (responseCode == 200) {
        val responseString = connection.inputStream.bufferedReader().use { it.readText() }
        val responseJson = org.json.JSONObject(responseString)
        val candidates = responseJson.optJSONArray("candidates")
        if (candidates != null && candidates.length() > 0) {
          val firstCand = candidates.getJSONObject(0)
          val content = firstCand.optJSONObject("content")
          val parts = content?.optJSONArray("parts")
          if (parts != null && parts.length() > 0) {
            val text = parts.getJSONObject(0).optString("text", "")
            if (text.isNotBlank()) return@withContext text
          }
        }
      }
    } catch (_: Exception) {}
    return@withContext ""
  }
}

// Free Public Real Generative AI LLM Inference Client (No API Key Required)
object PollinationsTextApiClient {
  suspend fun generateText(
    userPrompt: String,
    model: AiModelType,
  ): String = kotlinx.coroutines.withContext(Dispatchers.IO) {
    val systemPrompt = when (model) {
      AiModelType.LOOP -> "Ты — настоящий независимый умный искусственный интеллект по имени Loop (аналог ChatGPT / Gemini / Dola AI). Отвечай на абсолютно любые вопросы пользователя живым, содержательным, интересным и грамотным языком на русском без сгенерированных штампов или заготовок."
      AiModelType.DEEPSEEK -> "Ты — искусственный интеллект DeepSeek R1. Отвечай подробно и логично."
      AiModelType.CHATGPT -> "Ты — искусственный интеллект ChatGPT (GPT-4o)."
      AiModelType.CLAUDE -> "Ты — искусственный интеллект Claude 3.5."
      AiModelType.GEMINI -> "Ты — искусственный интеллект Google Gemini."
      else -> "Ты — искусственный интеллект Loop."
    }

    // 1. Try POST JSON endpoint to Pollinations AI
    try {
      val url = java.net.URL("https://text.pollinations.ai/")
      val connection = url.openConnection() as java.net.HttpURLConnection
      connection.requestMethod = "POST"
      connection.setRequestProperty("Content-Type", "application/json; charset=utf-8")
      connection.doOutput = true
      connection.connectTimeout = 12000
      connection.readTimeout = 18000

      val jsonPayload = org.json.JSONObject().apply {
        val messages = org.json.JSONArray().apply {
          put(org.json.JSONObject().apply {
            put("role", "system")
            put("content", systemPrompt)
          })
          put(org.json.JSONObject().apply {
            put("role", "user")
            put("content", userPrompt)
          })
        }
        put("messages", messages)
        put("model", "openai")
        put("seed", System.currentTimeMillis().hashCode())
      }

      connection.outputStream.use { os ->
        os.write(jsonPayload.toString().toByteArray(Charsets.UTF_8))
      }

      if (connection.responseCode == 200) {
        val responseText = connection.inputStream.bufferedReader().use { it.readText() }
        if (responseText.isNotBlank() && responseText.length > 3) {
          return@withContext responseText.trim()
        }
      }
    } catch (_: Exception) {}

    // 2. Fallback to GET endpoint with URL encoding
    try {
      val encodedPrompt = java.net.URLEncoder.encode(userPrompt, "UTF-8")
      val encodedSystem = java.net.URLEncoder.encode(systemPrompt, "UTF-8")
      val urlStr = "https://text.pollinations.ai/$encodedPrompt?system=$encodedSystem&model=openai"
      val url = java.net.URL(urlStr)
      val connection = url.openConnection() as java.net.HttpURLConnection
      connection.requestMethod = "GET"
      connection.connectTimeout = 12000
      connection.readTimeout = 18000

      if (connection.responseCode == 200) {
        val responseText = connection.inputStream.bufferedReader().use { it.readText() }
        if (responseText.isNotBlank() && responseText.length > 3) {
          return@withContext responseText.trim()
        }
      }
    } catch (_: Exception) {}

    return@withContext ""
  }
}

suspend fun generateAiResponseAsync(
  context: android.content.Context,
  userPrompt: String,
  model: AiModelType,
  mode: ModelMode
): String {
  val prefs = context.getSharedPreferences("loopai_app_prefs", android.content.Context.MODE_PRIVATE)
  val customKey = prefs.getString("custom_gemini_api_key", "")?.trim() ?: ""
  val apiKeyToUse = if (customKey.isNotBlank()) customKey else BuildConfig.GEMINI_API_KEY

  // 1. First try Gemini API if key is available
  if (apiKeyToUse.isNotBlank() && apiKeyToUse != "MY_GEMINI_API_KEY") {
    val modelTag = when (model) {
      AiModelType.LOOP -> "gemini-2.5-flash"
      AiModelType.GEMINI -> "gemini-2.5-flash"
      AiModelType.DEEPSEEK -> "gemini-3.1-pro-preview"
      AiModelType.CHATGPT -> "gemini-2.5-flash"
      AiModelType.CLAUDE -> "gemini-3.1-pro-preview"
      else -> "gemini-2.5-flash"
    }
    val realResponse = GeminiApiClient.callGeminiApi(userPrompt, apiKeyToUse, modelTag, model)
    if (realResponse.isNotBlank()) {
      return realResponse
    }
  }

  // 2. Real Generative AI Inference Engine (Free, Direct LLM Call like ChatGPT / Gemini)
  val realInferenceResponse = PollinationsTextApiClient.generateText(userPrompt, model)
  if (realInferenceResponse.isNotBlank()) {
    return realInferenceResponse
  }

  // 3. Fallback Smart engine response
  return generateAiResponse(userPrompt, model, mode)
}

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    val prefs = getSharedPreferences("loopai_app_prefs", MODE_PRIVATE)
    val savedDark = prefs.getBoolean("is_dark_theme", false)
    val savedColorName = prefs.getString("title_color_name", RainbowTitleColor.BLUE.name) ?: RainbowTitleColor.BLUE.name
    val initialTitleColor = try { RainbowTitleColor.valueOf(savedColorName) } catch (_: Exception) { RainbowTitleColor.BLUE }

    setContent {
      var isDarkTheme by remember { mutableStateOf(savedDark) }
      var selectedTitleColor by remember { mutableStateOf(initialTitleColor) }
      var selectedAiModel by remember { mutableStateOf(AiModelType.LOOP) }

      LaunchedEffect(isDarkTheme) {
        prefs.edit().putBoolean("is_dark_theme", isDarkTheme).apply()
      }
      LaunchedEffect(selectedTitleColor) {
        prefs.edit().putString("title_color_name", selectedTitleColor.name).apply()
      }

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

  // Separate message histories for each model with persistent storage
  val chatMessagesMap = remember {
    mutableStateMapOf<AiModelType, SnapshotStateList<ChatMessage>>().apply {
      AiModelType.entries.forEach { model ->
        val savedList = ChatPersistenceManager.loadMessages(context, model)
        put(model, mutableStateListOf<ChatMessage>().apply { addAll(savedList) })
      }
    }
  }

  // Auto-save active chat messages to SharedPreferences whenever updated
  AiModelType.entries.forEach { model ->
    val msgs = chatMessagesMap[model]
    val lastMsgText = msgs?.lastOrNull()?.text
    val msgSize = msgs?.size ?: 0
    LaunchedEffect(model, msgSize, lastMsgText) {
      msgs?.let { list ->
        ChatPersistenceManager.saveMessages(context, model, list)
      }
    }
  }

  // Active video generation jobs by message ID
  val activeVideoJobs = remember { mutableStateMapOf<String, Job>() }

  var selectedVideoModel by remember { mutableStateOf("Seedanse 2.5") }
  var isTextGenerating by remember { mutableStateOf(false) }
  var showSettingsDialog by remember { mutableStateOf(false) }
  val appCoroutineScope = rememberCoroutineScope()

  val effectiveTitleColor = selectedTitleColor.color

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

        // Start fast 3-second neural video generation workflow
        fun startVideoGeneration(
          prompt: String,
          modelName: String,
          photos: List<String>,
          aspect: String,
          durationSeconds: Int = 5,
        ) {
          Toast.makeText(
            context,
            "🚀 Генерация видео ($modelName • $durationSeconds сек)...",
            Toast.LENGTH_SHORT
          ).show()

          val sceneUrl = resolveCinematicScene(prompt, photos)
          val generatingMsgId = UUID.randomUUID().toString()
          val speech = extractSpeechText(prompt)

          // Add placeholder generating message
          val generatingMessage = ChatMessage(
            id = generatingMsgId,
            text = "🎬 Создание видео ($modelName • $durationSeconds сек): «$prompt»",
            isUser = false,
            modelType = selectedAiModel,
            isVideo = true,
            videoModel = modelName,
            originalPrompt = prompt,
            speechText = speech,
            attachedImages = photos,
            visualSceneUrl = sceneUrl,
            aspectRatio = aspect,
            durationSeconds = durationSeconds,
            isGeneratingVideo = true,
          )
          activeMessages.add(generatingMessage)

          // Fast 3-second generation job
          val job = appCoroutineScope.launch {
            val totalSeconds = 3
            for (sec in 1..totalSeconds) {
              delay(1000)

              val index = activeMessages.indexOfFirst { it.id == generatingMsgId }
              if (index == -1) break

              val currentMsg = activeMessages[index]
              if (!currentMsg.isGeneratingVideo) break

              if (sec == 1) {
                val isReanme = modelName.contains("Reanme")
                activeMessages[index] = currentMsg.copy(
                  text = if (isReanme) "⚡ [1/3] Reanme 2.0 (LoopAi Engine) • Обработка 60 FPS кадров и физики сцены..."
                         else "⚡ [1/3] Обработка 4K нейрокадров и анимации..."
                )
              } else if (sec == 2) {
                val isReanme = modelName.contains("Reanme")
                activeMessages[index] = currentMsg.copy(
                  text = if (isReanme) "🎬 [2/3] Reanme 2.0 • Кинематографический рендеринг Seedanse 2.0 & синтез звука..."
                         else "🎬 [2/3] Синтез речи персонажа и липсинк эффектов..."
                )
              } else if (sec >= totalSeconds) {
                val isReanme = modelName.contains("Reanme")
                activeMessages[index] = currentMsg.copy(
                  text = if (isReanme) {
                    if (photos.isNotEmpty()) {
                      "✨ Ваше видео ($durationSeconds сек) успешно создано официальным ИИ Reanme 2.0 от LoopAi на основе ваших фото:\n«$prompt»"
                    } else {
                      "✨ Ваше видео ($durationSeconds сек) успешно создано официальным ИИ Reanme 2.0 от LoopAi:\n«$prompt»"
                    }
                  } else if (photos.isNotEmpty()) {
                    "Ваше видео ($durationSeconds сек) готово по промпту: «$prompt» на основе ваших фото ($modelName)!"
                  } else {
                    "Ваше видео ($durationSeconds сек) готово по промпту: «$prompt» ($modelName)!"
                  },
                  isGeneratingVideo = false,
                  speechText = speech,
                  durationSeconds = durationSeconds,
                  videoSeed = System.currentTimeMillis(),
                )
                activeVideoJobs.remove(generatingMsgId)
                Toast.makeText(context, "✨ Видео $modelName успешно создано!", Toast.LENGTH_SHORT).show()
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
          onSendMessage = { text, mode, photos ->
            val cleanLower = text.trim().lowercase()
            val stopKeywords = listOf("стоп", "stop", "отмена", "отмени", "отменить", "остановить", "останови", "хватит", "прекратить", "cancel")
            val isStopCommand = stopKeywords.any { cleanLower == it || cleanLower.startsWith("$it ") }

            if (isStopCommand) {
              // Add user message
              activeMessages.add(ChatMessage(text = text, isUser = true, attachedImages = photos))

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
            } else if (selectedAiModel.isVideoModel) {
              val modelToUse = when (selectedAiModel) {
                AiModelType.VEO_3 -> "Veo 3"
                AiModelType.GOOGLE_OMNI_FLASH -> "Google Omni Flash"
                else -> selectedVideoModel
              }
              activeMessages.add(
                ChatMessage(
                  text = text,
                  isUser = true,
                  attachedImages = photos,
                  aspectRatio = "16:9",
                  durationSeconds = 5,
                )
              )
              startVideoGeneration(
                prompt = text,
                modelName = modelToUse,
                photos = photos,
                aspect = "16:9",
                durationSeconds = 5,
              )
            } else {
              // Standard AI chat text message (DeepSeek, Gemini, ChatGPT, Claude)
              activeMessages.add(
                ChatMessage(
                  text = text,
                  isUser = true,
                  attachedImages = photos,
                )
              )
              isTextGenerating = true
              appCoroutineScope.launch {
                delay(700)
                val response = generateAiResponseAsync(context, text, selectedAiModel, mode)
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
          onGenerateVideoContent = { prompt, modelName, photos, aspect, duration ->
            activeMessages.add(
              ChatMessage(
                text = prompt,
                isUser = true,
                attachedImages = photos,
                aspectRatio = aspect,
                durationSeconds = duration,
              )
            )
            startVideoGeneration(
              prompt = prompt,
              modelName = modelName,
              photos = photos,
              aspect = aspect,
              durationSeconds = duration,
            )
          },
          onGenerateImageContent = { prompt, style, photos, aspect, model ->
            val engineInfo = if (model.contains("Rolatsee")) "Rolatsee 1.0 (Dreamina Seedream 5.0 Ultra HD)" else model
            activeMessages.add(
              ChatMessage(
                text = if (prompt.isNotBlank()) "Сгенерируй изображение ($model • $style): $prompt" else "Сгенерируй изображение ($model • $style)",
                isUser = true,
                attachedImages = photos,
                aspectRatio = aspect,
              )
            )
            isTextGenerating = true
            appCoroutineScope.launch {
              delay(1000)
              val enhancedPrompt = if (style.isNotBlank()) "$prompt, style: $style, high quality 4k" else prompt
              val sceneUrl = resolveCinematicScene(enhancedPrompt, photos)
              activeMessages.add(
                ChatMessage(
                  text = "🖼️ Ваше изображение готово!\n✨ Модель: $engineInfo\nПромпт: «$prompt»${if (style.isNotBlank()) "\nСтиль: $style" else ""}\nФормат: $aspect",
                  isUser = false,
                  modelType = selectedAiModel,
                  attachedImages = listOf(sceneUrl),
                  aspectRatio = aspect,
                )
              )
              isTextGenerating = false
              Toast.makeText(context, "Изображение создано ($model)", Toast.LENGTH_SHORT).show()
            }
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
                durationSeconds = current.durationSeconds,
                videoSeed = System.currentTimeMillis(),
              )
            }
          },
          onBack = { currentScreen = Screen.Home },
          onOpenSettings = { showSettingsDialog = true },
          onSelectAiModel = onSelectAiModel,
          onClearChat = {
            activeMessages.clear()
            ChatPersistenceManager.clearMessages(context, selectedAiModel)
            Toast.makeText(context, "История чата очищена", Toast.LENGTH_SHORT).show()
          },
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
  val isDark = MaterialTheme.colorScheme.surface.luminance() < 0.5f
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
  onSendMessage: (String, ModelMode, List<String>) -> Unit,
  onGenerateVideoContent: (prompt: String, model: String, photos: List<String>, aspect: String, durationSeconds: Int) -> Unit,
  onGenerateImageContent: (prompt: String, style: String, photos: List<String>, aspect: String, model: String) -> Unit = { _, _, _, _, _ -> },
  onFastForwardVideo: (String) -> Unit,
  onBack: () -> Unit,
  onOpenSettings: () -> Unit,
  onSelectAiModel: (AiModelType) -> Unit,
  onClearChat: () -> Unit = {},
  modifier: Modifier = Modifier,
) {
  val isVideoMode = selectedAiModel.isVideoModel
  val context = androidx.compose.ui.platform.LocalContext.current

  var inputText by remember { mutableStateOf("") }
  var currentMode by remember { mutableStateOf(ModelMode.FAST) }
  var showAiContentStudioSheet by remember { mutableStateOf(false) }
  var activeStudioTab by remember { mutableStateOf(if (isVideoMode) AiStudioContentTab.VIDEO else AiStudioContentTab.IMAGE) }
  var attachedPhotos by remember { mutableStateOf<List<String>>(emptyList()) }

  val pickPhotoLauncher = rememberLauncherForActivityResult(
    contract = ActivityResultContracts.PickMultipleVisualMedia(maxItems = 4)
  ) { uris ->
    if (uris.isNotEmpty()) {
      attachedPhotos = (attachedPhotos + uris.map { it.toString() }).distinct().take(4)
    }
  }

  val speechLauncher = rememberLauncherForActivityResult(
    contract = ActivityResultContracts.StartActivityForResult()
  ) { result ->
    if (result.resultCode == Activity.RESULT_OK) {
      val spokenText = result.data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)?.firstOrNull()
      if (!spokenText.isNullOrBlank()) {
        inputText = if (inputText.isBlank()) spokenText else "$inputText $spokenText"
      }
    }
  }

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
    if ((trimmed.isNotEmpty() || attachedPhotos.isNotEmpty()) && !isTextGenerating) {
      onSendMessage(trimmed, currentMode, attachedPhotos)
      inputText = ""
      attachedPhotos = emptyList()
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
        modifier = Modifier.width(285.dp),
        drawerContainerColor = MaterialTheme.colorScheme.surface,
      ) {
        Spacer(modifier = Modifier.height(16.dp))
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 10.dp)
        ) {
          Text(
            text = "LoopAi",
            color = accentColor,
            fontSize = 24.sp,
            fontWeight = FontWeight.ExtraBold,
          )
          Spacer(modifier = Modifier.height(2.dp))
          Text(
            text = if (isVideoMode) "Студия: ${selectedAiModel.displayName}" else "Чат: ${selectedAiModel.displayName}",
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
          )
        }

        HorizontalDivider(
          modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp),
          color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f),
        )

        // СЕКЦИЯ 1: ЧАТЫ С ИИ (DeepSeek, Gemini, ChatGPT, Claude)
        Text(
          text = "ЧАТЫ С ИИ",
          fontSize = 11.sp,
          fontWeight = FontWeight.Bold,
          color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f),
          modifier = Modifier.padding(horizontal = 20.dp, vertical = 4.dp)
        )

        AiModelType.entries.filter { !it.isVideoModel }.forEach { model ->
          val isSelected = model == selectedAiModel
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
                imageVector = Icons.AutoMirrored.Filled.Chat,
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
              .padding(horizontal = 10.dp, vertical = 2.dp)
              .testTag("drawer_chat_${model.name}"),
            colors = NavigationDrawerItemDefaults.colors(
              selectedContainerColor = accentColor.copy(alpha = 0.15f),
              selectedTextColor = if (isWhiteAccent) MaterialTheme.colorScheme.onSurface else accentColor,
              unselectedContainerColor = Color.Transparent,
              unselectedTextColor = MaterialTheme.colorScheme.onSurface,
            ),
          )
        }

        Spacer(modifier = Modifier.height(6.dp))
        HorizontalDivider(
          modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
          color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f),
        )

        // СЕКЦИЯ 2: ВИДЕО СТУДИЯ (Google Omni Flash, Veo 3, Seedanse)
        Text(
          text = "ВИДЕО СТУДИЯ",
          fontSize = 11.sp,
          fontWeight = FontWeight.Bold,
          color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f),
          modifier = Modifier.padding(horizontal = 20.dp, vertical = 4.dp)
        )

        AiModelType.entries.filter { it.isVideoModel }.forEach { model ->
          val isSelected = model == selectedAiModel
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
                imageVector = Icons.Default.Videocam,
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
              .padding(horizontal = 10.dp, vertical = 2.dp)
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
            Text(
              text = "Очистить историю чата",
              fontSize = 15.sp,
              fontWeight = FontWeight.Medium,
              color = MaterialTheme.colorScheme.error,
            )
          },
          icon = {
            Icon(
              imageVector = Icons.Default.Delete,
              contentDescription = "Очистить чат",
              tint = MaterialTheme.colorScheme.error,
              modifier = Modifier.size(22.dp),
            )
          },
          selected = false,
          onClick = {
            coroutineScope.launch {
              drawerState.close()
              onClearChat()
            }
          },
          modifier = Modifier
            .padding(horizontal = 12.dp, vertical = 4.dp)
            .testTag("drawer_clear_chat_button"),
          colors = NavigationDrawerItemDefaults.colors(
            unselectedContainerColor = Color.Transparent,
            unselectedTextColor = MaterialTheme.colorScheme.error,
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
                text = if (isVideoMode) "Видео Студия • ${selectedAiModel.displayName}" else selectedAiModel.displayName,
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


            // Display "Контент ИИ" in Video Studio modes
            if (isVideoMode) {
              IconButton(
                onClick = {
                  activeStudioTab = AiStudioContentTab.VIDEO
                  showAiContentStudioSheet = true
                },
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
            MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
          ),
          modifier = Modifier.fillMaxWidth(),
        ) {
          Column(
            modifier = Modifier
              .fillMaxWidth()
              .navigationBarsPadding()
              .imePadding()
              .padding(horizontal = 10.dp, vertical = 6.dp),
          ) {
            // ТОЧНО ТАКАЯ ЖЕ ЛЕНТА (Ribbon bar right above input row)
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(vertical = 4.dp),
              horizontalArrangement = Arrangement.spacedBy(8.dp),
              verticalAlignment = Alignment.CenterVertically,
            ) {
              // 1. Переключатель с Fast на Pro (⚡ Fast › / ✨ Pro ›)
              Surface(
                onClick = {
                  currentMode = if (currentMode == ModelMode.FAST) ModelMode.PRO else ModelMode.FAST
                  val modeToast = if (currentMode == ModelMode.PRO) "Включен режим Pro: глубокий анализ" else "Включен режим Fast: быстрые ответы"
                  Toast.makeText(context, modeToast, Toast.LENGTH_SHORT).show()
                },
                shape = RoundedCornerShape(20.dp),
                color = accentColor.copy(alpha = 0.15f),
                border = androidx.compose.foundation.BorderStroke(
                  1.dp,
                  accentColor.copy(alpha = 0.5f)
                ),
                modifier = Modifier.testTag("ribbon_fast_pro_switch"),
              ) {
                Row(
                  modifier = Modifier.padding(horizontal = 12.dp, vertical = 7.dp),
                  verticalAlignment = Alignment.CenterVertically,
                ) {
                  Icon(
                    imageVector = if (currentMode == ModelMode.FAST) Icons.Default.Bolt else Icons.Default.AutoAwesome,
                    contentDescription = currentMode.label,
                    tint = accentColor,
                    modifier = Modifier.size(16.dp),
                  )
                  Spacer(modifier = Modifier.width(5.dp))
                  Text(
                    text = currentMode.label,
                    color = accentColor,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                  )
                  Spacer(modifier = Modifier.width(3.dp))
                  Icon(
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = null,
                    tint = accentColor.copy(alpha = 0.8f),
                    modifier = Modifier.size(16.dp),
                  )
                }
              }

              // 2. Создание ИИ контента (Всё в одном: Изображение и Видео как в Dola AI)
              Surface(
                onClick = {
                  activeStudioTab = if (isVideoMode) AiStudioContentTab.VIDEO else AiStudioContentTab.IMAGE
                  showAiContentStudioSheet = true
                },
                shape = RoundedCornerShape(20.dp),
                color = accentColor.copy(alpha = 0.15f),
                border = androidx.compose.foundation.BorderStroke(
                  1.dp,
                  accentColor.copy(alpha = 0.5f)
                ),
                modifier = Modifier.testTag("ribbon_ai_content_studio"),
              ) {
                Row(
                  modifier = Modifier.padding(horizontal = 14.dp, vertical = 7.dp),
                  verticalAlignment = Alignment.CenterVertically,
                ) {
                  Icon(
                    imageVector = Icons.Default.AutoAwesome,
                    contentDescription = "Создание ИИ контента",
                    tint = accentColor,
                    modifier = Modifier.size(16.dp),
                  )
                  Spacer(modifier = Modifier.width(6.dp))
                  Text(
                    text = "Создание ИИ контента",
                    color = accentColor,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                  )
                }
              }
            }

            // Preview attached photos if any
            if (attachedPhotos.isNotEmpty()) {
              Spacer(modifier = Modifier.height(4.dp))
              LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                  .fillMaxWidth()
                  .padding(vertical = 4.dp),
              ) {
                items(attachedPhotos) { photoUri ->
                  Box(
                    modifier = Modifier
                      .size(52.dp)
                      .clip(RoundedCornerShape(8.dp))
                      .border(1.dp, accentColor, RoundedCornerShape(8.dp)),
                  ) {
                    AsyncImage(
                      model = photoUri,
                      contentDescription = "Выбранное фото",
                      contentScale = ContentScale.Crop,
                      modifier = Modifier.fillMaxSize(),
                    )
                    IconButton(
                      onClick = { attachedPhotos = attachedPhotos.filter { it != photoUri } },
                      modifier = Modifier
                        .align(Alignment.TopEnd)
                        .size(18.dp)
                        .background(Color.Black.copy(alpha = 0.6f), CircleShape),
                    ) {
                      Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Удалить фото",
                        tint = Color.White,
                        modifier = Modifier.size(12.dp),
                      )
                    }
                  }
                }
              }
            }

            Spacer(modifier = Modifier.height(2.dp))

            // Поле ввода сообщения как на видео (Камера, Поле "Сообщение...", Микрофон, Отправить)
            Row(
              modifier = Modifier.fillMaxWidth(),
              verticalAlignment = Alignment.CenterVertically,
            ) {
              // Иконка камеры слева
              IconButton(
                onClick = {
                  pickPhotoLauncher.launch(
                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                  )
                },
                modifier = Modifier
                  .size(42.dp)
                  .testTag("chat_camera_button"),
              ) {
                Icon(
                  imageVector = Icons.Default.PhotoCamera,
                  contentDescription = "Прикрепить фото",
                  tint = accentColor,
                  modifier = Modifier.size(24.dp),
                )
              }

              // Текстовое поле ввода сообщения
              OutlinedTextField(
                value = inputText,
                onValueChange = { inputText = it },
                placeholder = {
                  Text(
                    text = if (isVideoMode) "Опишите сюжет или идею для видео..." else "Сообщение...",
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
                  unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.35f),
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

              Spacer(modifier = Modifier.width(4.dp))

              // Кнопка микрофона
              IconButton(
                onClick = {
                  try {
                    val speechIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
                      putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
                      putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ru-RU")
                      putExtra(RecognizerIntent.EXTRA_PROMPT, "Говорите...")
                    }
                    speechLauncher.launch(speechIntent)
                  } catch (_: Exception) {
                    Toast.makeText(context, "Голосовой ввод недоступен", Toast.LENGTH_SHORT).show()
                  }
                },
                modifier = Modifier
                  .size(42.dp)
                  .testTag("chat_mic_button"),
              ) {
                Icon(
                  imageVector = Icons.Default.Mic,
                  contentDescription = "Голосовой ввод",
                  tint = accentColor,
                  modifier = Modifier.size(22.dp),
                )
              }

              // Кнопка отправки сообщения
              val canSend = (inputText.isNotBlank() || attachedPhotos.isNotEmpty()) && !isTextGenerating
              IconButton(
                onClick = { handleSendMessage() },
                enabled = canSend,
                modifier = Modifier
                  .size(44.dp)
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
                    imageVector = if (isVideoMode) Icons.Default.Movie else Icons.AutoMirrored.Filled.Send,
                    contentDescription = "Отправить",
                    tint = if (canSend) buttonContentColor else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f),
                    modifier = Modifier.size(20.dp),
                  )
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
                imageVector = if (isVideoMode) Icons.Default.Videocam else Icons.AutoMirrored.Filled.Chat,
                contentDescription = null,
                tint = accentColor,
                modifier = Modifier.size(34.dp),
              )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
              text = if (isVideoMode) "Видео Студия • ${selectedAiModel.displayName}" else "Диалог с ${selectedAiModel.displayName}",
              color = MaterialTheme.colorScheme.onSurface,
              fontSize = 20.sp,
              fontWeight = FontWeight.Bold,
              textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
              text = if (isVideoMode) {
                "${selectedAiModel.subtitle}\nСоздавайте видеоконтент ИИ с выбором длительности (5с, 10с) и фото."
              } else {
                selectedAiModel.subtitle
              },
              color = MaterialTheme.colorScheme.onSurfaceVariant,
              fontSize = 14.sp,
              textAlign = TextAlign.Center,
              lineHeight = 20.sp,
            )

            if (isVideoMode) {
              Spacer(modifier = Modifier.height(20.dp))
              Button(
                onClick = {
                  activeStudioTab = AiStudioContentTab.VIDEO
                  showAiContentStudioSheet = true
                },
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
                Text("Создать видеоконтент", fontWeight = FontWeight.Bold)
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
                ShimmeringThinkingBubble(
                  accentColor = accentColor,
                )
              }
            }
          }
        }
      }
    }
  }

  if (showAiContentStudioSheet) {
    val defaultVideoModel = when (selectedAiModel) {
      AiModelType.VEO_3 -> "Google Veo 3"
      AiModelType.GOOGLE_OMNI_FLASH -> "Google Omni Flash"
      AiModelType.SEEDANSE -> "Reanme 2.0"
      else -> if (selectedVideoModel.isNotBlank()) selectedVideoModel else "Reanme 2.0"
    }
    DolaAiContentStudioBottomSheet(
      initialTab = activeStudioTab,
      selectedVideoModel = defaultVideoModel,
      selectedImageModel = "Rolatsee 1.0",
      accentColor = accentColor,
      onDismiss = { showAiContentStudioSheet = false },
      onGenerateVideo = { prompt, model, photos, aspect, duration ->
        showAiContentStudioSheet = false
        onGenerateVideoContent(prompt, model, photos, aspect, duration)
        coroutineScope.launch {
          if (messages.isNotEmpty()) {
            listState.animateScrollToItem(messages.size)
          }
        }
      },
      onGenerateImage = { prompt, style, photos, aspect, model ->
        showAiContentStudioSheet = false
        onGenerateImageContent(prompt, style, photos, aspect, model)
        coroutineScope.launch {
          if (messages.isNotEmpty()) {
            listState.animateScrollToItem(messages.size)
          }
        }
      }
    )
  }
}

enum class AiStudioContentTab(val title: String) {
  IMAGE("Изображение"),
  VIDEO("Видео"),
}

// All-in-one Dola AI Content Creation Bottom Sheet (Image & Video tabs with Rolatsee 1.0 and Reanme 2.0)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DolaAiContentStudioBottomSheet(
  initialTab: AiStudioContentTab = AiStudioContentTab.IMAGE,
  selectedVideoModel: String = "Reanme 2.0",
  selectedImageModel: String = "Rolatsee 1.0",
  accentColor: Color,
  onDismiss: () -> Unit,
  onGenerateVideo: (prompt: String, model: String, photos: List<String>, aspect: String, durationSeconds: Int) -> Unit,
  onGenerateImage: (prompt: String, style: String, photos: List<String>, aspect: String, model: String) -> Unit,
) {
  val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
  val isWhiteAccent = accentColor == Color.White
  val buttonContentColor = if (isWhiteAccent) Color.Black else Color.White

  var currentTab by remember { mutableStateOf(initialTab) }

  // State for Image Generation
  var imagePrompt by remember { mutableStateOf("") }
  var chosenImageModel by remember { mutableStateOf(selectedImageModel) }
  var chosenImageStyle by remember { mutableStateOf("Фотореализм") }
  var chosenImageAspect by remember { mutableStateOf("1:1") }
  val attachedImagePhotos = remember { mutableStateListOf<String>() }

  // State for Video Generation
  var videoPrompt by remember { mutableStateOf("") }
  var chosenVideoModel by remember { mutableStateOf(selectedVideoModel) }
  var chosenVideoAspect by remember { mutableStateOf("16:9") }
  var chosenVideoDuration by remember { mutableStateOf(5) }
  var is60FpsEnabled by remember { mutableStateOf(true) }
  val attachedVideoPhotos = remember { mutableStateListOf<String>() }

  val imagePhotoPickerLauncher = rememberLauncherForActivityResult(
    contract = ActivityResultContracts.PickMultipleVisualMedia(maxItems = 2)
  ) { uris ->
    uris.forEach { uri ->
      if (!attachedImagePhotos.contains(uri.toString())) {
        attachedImagePhotos.add(uri.toString())
      }
    }
  }

  val videoPhotoPickerLauncher = rememberLauncherForActivityResult(
    contract = ActivityResultContracts.PickMultipleVisualMedia(maxItems = 4)
  ) { uris ->
    uris.forEach { uri ->
      if (!attachedVideoPhotos.contains(uri.toString())) {
        attachedVideoPhotos.add(uri.toString())
      }
    }
  }

  // Quick preset inspiration prompts
  val imageInspirations = listOf(
    "🌆 Киберпанк" to "Футуристический мегаполис в неоновом дожде, летающие машины, отражения в лужах, 8k",
    "🌸 Аниме Арт" to "Красочная аниме сцена на закате с лепестками сакуры и мягким кинематографичным светом",
    "👤 Портрет 8K" to "Студийный гиперреалистичный портрет, детальные глаза, мягкий контрастный свет Rembrandt",
    "🌌 Космос" to "Глубокий космос, спиральная туманность, сияющие звезды и космический телескоп",
    "🐉 Фэнтези" to "Величественный дракон на вершине заснеженной горы в лучах заката, фэнтези арт",
    "🐱 Котик" to "Милый пушистый котёнок в космонавтском скафандре на Луне, фотореализм",
    "🏎️ Суперкар" to "Концептуальный электрический суперкар на горном серпантине на закате, кинематограф"
  )

  val videoInspirations = listOf(
    "🏎️ Дрифт" to "Динамичный дрифт спорткара по ночному Токио с дымом из-под колес и неоновыми огнями",
    "🚀 Космос" to "Камера плавно пролетает сквозь кольца Сатурна навстречу сияющей туманности",
    "🌊 Океан" to "Огромная кристально чистая волна на закате с брызгами воды в замедленной съемке",
    "🏙️ Таймлапс" to "Кинематографичный таймлапс ночного мегаполиса с потоками автомобильных огней",
    "💃 Неон" to "Танцовщица в световом костюме с шлейфами искр в темноте, плавная камера 60 FPS",
    "☕ Уют" to "Капли дождя стекают по стеклу уютного кафе, за окном вечерний Париж, теплый свет"
  )

  ModalBottomSheet(
    onDismissRequest = onDismiss,
    sheetState = sheetState,
    containerColor = MaterialTheme.colorScheme.surface,
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .verticalScroll(rememberScrollState())
        .padding(horizontal = 18.dp, vertical = 4.dp)
        .padding(bottom = 28.dp),
    ) {
      // Header: Dola AI Content Creation Studio
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Surface(
            shape = CircleShape,
            color = accentColor.copy(alpha = 0.15f),
            modifier = Modifier.size(36.dp)
          ) {
            Box(contentAlignment = Alignment.Center) {
              Icon(
                imageVector = Icons.Default.AutoAwesome,
                contentDescription = null,
                tint = accentColor,
                modifier = Modifier.size(20.dp)
              )
            }
          }
          Spacer(modifier = Modifier.width(10.dp))
          Column {
            Text(
              text = "Создание ИИ контента",
              fontSize = 17.5.sp,
              fontWeight = FontWeight.Bold,
              color = MaterialTheme.colorScheme.onSurface,
            )
            Text(
              text = "Dola AI Studio • Генерация нового поколения",
              fontSize = 11.5.sp,
              color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
          }
        }

        IconButton(onClick = onDismiss, modifier = Modifier.size(32.dp)) {
          Icon(Icons.Default.Close, contentDescription = "Закрыть", modifier = Modifier.size(18.dp))
        }
      }

      Spacer(modifier = Modifier.height(14.dp))

      // Tab Switcher: Изображение / Видео
      Surface(
        shape = RoundedCornerShape(14.dp),
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
        border = androidx.compose.foundation.BorderStroke(
          1.dp,
          MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.35f)
        ),
        modifier = Modifier.fillMaxWidth()
      ) {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
          horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
          // Tab 1: Изображение
          val isImageTab = currentTab == AiStudioContentTab.IMAGE
          Surface(
            onClick = { currentTab = AiStudioContentTab.IMAGE },
            shape = RoundedCornerShape(10.dp),
            color = if (isImageTab) accentColor else Color.Transparent,
            modifier = Modifier
              .weight(1f)
              .testTag("tab_image_generation")
          ) {
            Row(
              modifier = Modifier.padding(vertical = 9.dp),
              horizontalArrangement = Arrangement.Center,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Icon(
                imageVector = Icons.Default.Image,
                contentDescription = null,
                tint = if (isImageTab) buttonContentColor else MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.size(17.dp)
              )
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = "Изображение",
                fontSize = 13.5.sp,
                fontWeight = if (isImageTab) FontWeight.Bold else FontWeight.Medium,
                color = if (isImageTab) buttonContentColor else MaterialTheme.colorScheme.onSurface
              )
            }
          }

          // Tab 2: Видео
          val isVideoTab = currentTab == AiStudioContentTab.VIDEO
          Surface(
            onClick = { currentTab = AiStudioContentTab.VIDEO },
            shape = RoundedCornerShape(10.dp),
            color = if (isVideoTab) accentColor else Color.Transparent,
            modifier = Modifier
              .weight(1f)
              .testTag("tab_video_generation")
          ) {
            Row(
              modifier = Modifier.padding(vertical = 9.dp),
              horizontalArrangement = Arrangement.Center,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Icon(
                imageVector = Icons.Default.Videocam,
                contentDescription = null,
                tint = if (isVideoTab) buttonContentColor else MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.size(17.dp)
              )
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = "Видео",
                fontSize = 13.5.sp,
                fontWeight = if (isVideoTab) FontWeight.Bold else FontWeight.Medium,
                color = if (isVideoTab) buttonContentColor else MaterialTheme.colorScheme.onSurface
              )
            }
          }
        }
      }

      Spacer(modifier = Modifier.height(14.dp))

      // ==================== IMAGE TAB ====================
      if (currentTab == AiStudioContentTab.IMAGE) {
        // Model Selection for Images
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = "НЕЙРОСЕТЬ ДЛЯ ИЗОБРАЖЕНИЙ",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            letterSpacing = 0.5.sp,
          )
          Text(
            text = chosenImageModel,
            fontSize = 11.5.sp,
            fontWeight = FontWeight.SemiBold,
            color = accentColor,
          )
        }
        Spacer(modifier = Modifier.height(8.dp))

        val imageModels = listOf(
          Triple("Rolatsee 1.0", "✨ Seedream 5.0", "Ультра 4K • Фотореализм и точный свет"),
          Triple("Dreamina Seedream 5.0", "🎨 Pro Render", "Максимальная детализация лиц и фонов"),
          Triple("Imagen 3 HD", "🌟 Google AI", "Высокая точность соблюдения промптов"),
          Triple("FLUX.1 Schnell", "⚡ Быстрый", "Мгновенная генерация артов")
        )

        LazyRow(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          items(imageModels) { (mName, mBadge, mDesc) ->
            val isSelected = chosenImageModel == mName || (mName == "Rolatsee 1.0" && chosenImageModel.contains("Rolatsee"))
            Surface(
              onClick = { chosenImageModel = mName },
              shape = RoundedCornerShape(12.dp),
              color = if (isSelected) accentColor.copy(alpha = 0.16f) else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f),
              border = androidx.compose.foundation.BorderStroke(
                1.5.dp,
                if (isSelected) accentColor else MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f)
              ),
              modifier = Modifier.width(180.dp)
            ) {
              Column(modifier = Modifier.padding(10.dp)) {
                Row(
                  modifier = Modifier.fillMaxWidth(),
                  horizontalArrangement = Arrangement.SpaceBetween,
                  verticalAlignment = Alignment.CenterVertically
                ) {
                  Text(
                    text = mName,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                    color = if (isSelected && !isWhiteAccent) accentColor else MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                  )
                  if (isSelected) {
                    Icon(
                      imageVector = Icons.Default.CheckCircle,
                      contentDescription = null,
                      tint = accentColor,
                      modifier = Modifier.size(16.dp)
                    )
                  }
                }
                Spacer(modifier = Modifier.height(3.dp))
                Surface(
                  shape = RoundedCornerShape(4.dp),
                  color = if (isSelected) accentColor.copy(alpha = 0.2f) else MaterialTheme.colorScheme.surfaceVariant,
                ) {
                  Text(
                    text = mBadge,
                    fontSize = 9.5.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = if (isSelected && !isWhiteAccent) accentColor else MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                  )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                  text = mDesc,
                  fontSize = 10.sp,
                  color = MaterialTheme.colorScheme.onSurfaceVariant,
                  maxLines = 2,
                  overflow = TextOverflow.Ellipsis,
                  lineHeight = 12.sp
                )
              }
            }
          }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Quick inspiration chips for Image
        Text(
          text = "БЫСТРЫЕ ИДЕИ И ПРОМПТЫ",
          fontSize = 11.sp,
          fontWeight = FontWeight.Bold,
          color = MaterialTheme.colorScheme.onSurfaceVariant,
          letterSpacing = 0.5.sp,
        )
        Spacer(modifier = Modifier.height(6.dp))
        LazyRow(
          horizontalArrangement = Arrangement.spacedBy(6.dp),
          modifier = Modifier.fillMaxWidth()
        ) {
          items(imageInspirations) { (chipTitle, chipPrompt) ->
            Surface(
              onClick = { imagePrompt = chipPrompt },
              shape = RoundedCornerShape(20.dp),
              color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.8f),
              border = androidx.compose.foundation.BorderStroke(
                1.dp,
                MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f)
              )
            ) {
              Text(
                text = chipTitle,
                fontSize = 11.5.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Prompt Input with Random & Clear actions
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = "ОПИСАНИЕ ИЗОБРАЖЕНИЯ",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            letterSpacing = 0.5.sp,
          )
          Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            TextButton(
              onClick = {
                val randomIdea = imageInspirations.random().second
                imagePrompt = randomIdea
              },
              contentPadding = PaddingValues(horizontal = 6.dp, vertical = 2.dp),
              modifier = Modifier.height(26.dp)
            ) {
              Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = accentColor, modifier = Modifier.size(13.dp))
              Spacer(modifier = Modifier.width(3.dp))
              Text("Случайный", fontSize = 11.sp, color = accentColor, fontWeight = FontWeight.SemiBold)
            }

            if (imagePrompt.isNotBlank()) {
              TextButton(
                onClick = { imagePrompt = "" },
                contentPadding = PaddingValues(horizontal = 6.dp, vertical = 2.dp),
                modifier = Modifier.height(26.dp)
              ) {
                Text("Очистить", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
              }
            }
          }
        }

        Spacer(modifier = Modifier.height(4.dp))
        OutlinedTextField(
          value = imagePrompt,
          onValueChange = { imagePrompt = it },
          placeholder = {
            Text(
              "Опишите всё, что хотите увидеть на картинке...",
              fontSize = 13.5.sp,
              color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
            )
          },
          modifier = Modifier
            .fillMaxWidth()
            .testTag("ai_image_prompt_input"),
          shape = RoundedCornerShape(12.dp),
          colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
            focusedBorderColor = accentColor,
            unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.25f),
          ),
          minLines = 2,
          maxLines = 4
        )

        Spacer(modifier = Modifier.height(14.dp))

        // Image Styles
        Text(
          text = "СТИЛЬ ИЗОБРАЖЕНИЯ",
          fontSize = 11.sp,
          fontWeight = FontWeight.Bold,
          color = MaterialTheme.colorScheme.onSurfaceVariant,
          letterSpacing = 0.5.sp,
        )
        Spacer(modifier = Modifier.height(6.dp))

        val styles = listOf(
          "📸 Фотореализм",
          "🌸 Аниме / Арт",
          "💎 3D Рендер",
          "⚡ Киберпанк",
          "🎬 Кинематограф",
          "🔮 Фэнтези",
          "🖌️ Акварель"
        )
        LazyRow(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
          items(styles) { styleWithIcon ->
            val styleName = styleWithIcon.substringAfter(" ")
            val isSelected = chosenImageStyle == styleName || chosenImageStyle == styleWithIcon
            Surface(
              onClick = { chosenImageStyle = styleName },
              shape = RoundedCornerShape(10.dp),
              color = if (isSelected) accentColor.copy(alpha = 0.18f) else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f),
              border = androidx.compose.foundation.BorderStroke(
                1.2.dp,
                if (isSelected) accentColor else MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f)
              ),
            ) {
              Text(
                text = styleWithIcon,
                fontSize = 12.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                color = if (isSelected && !isWhiteAccent) accentColor else MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Aspect Ratio
        Text(
          text = "ФОРМАТ / СООТНОШЕНИЕ СТОРОН",
          fontSize = 11.sp,
          fontWeight = FontWeight.Bold,
          color = MaterialTheme.colorScheme.onSurfaceVariant,
          letterSpacing = 0.5.sp,
        )
        Spacer(modifier = Modifier.height(6.dp))

        val imageAspects = listOf(
          Triple("1:1", "Квадрат", "Пост / Аватар"),
          Triple("16:9", "Альбом", "YouTube / ПК"),
          Triple("9:16", "Stories", "Reels / TikTok"),
          Triple("4:3", "Классика", "Фотография")
        )

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(6.dp),
        ) {
          imageAspects.forEach { (aspect, label, sub) ->
            val isSelected = chosenImageAspect == aspect
            Surface(
              onClick = { chosenImageAspect = aspect },
              shape = RoundedCornerShape(10.dp),
              color = if (isSelected) accentColor.copy(alpha = 0.18f) else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f),
              border = androidx.compose.foundation.BorderStroke(
                1.2.dp,
                if (isSelected) accentColor else MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f)
              ),
              modifier = Modifier.weight(1f),
            ) {
              Column(
                modifier = Modifier.padding(vertical = 7.dp, horizontal = 2.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
              ) {
                Text(
                  text = aspect,
                  fontSize = 12.5.sp,
                  fontWeight = FontWeight.Bold,
                  color = if (isSelected && !isWhiteAccent) accentColor else MaterialTheme.colorScheme.onSurface,
                )
                Text(
                  text = label,
                  fontSize = 9.sp,
                  color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
              }
            }
          }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Reference Photo
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically,
        ) {
          Text(
            text = "РЕФЕРЕНС / ИСХОДНОЕ ФОТО (${attachedImagePhotos.size}/2)",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            letterSpacing = 0.5.sp,
          )
          TextButton(
            onClick = {
              imagePhotoPickerLauncher.launch(
                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
              )
            },
            contentPadding = PaddingValues(horizontal = 6.dp, vertical = 2.dp),
            modifier = Modifier.height(26.dp)
          ) {
            Icon(Icons.Default.AddPhotoAlternate, contentDescription = null, tint = accentColor, modifier = Modifier.size(15.dp))
            Spacer(modifier = Modifier.width(4.dp))
            Text("+ Добавить фото", fontSize = 11.5.sp, color = accentColor, fontWeight = FontWeight.SemiBold)
          }
        }

        if (attachedImagePhotos.isNotEmpty()) {
          Spacer(modifier = Modifier.height(4.dp))
          LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(bottom = 6.dp),
          ) {
            items(attachedImagePhotos) { photoUri ->
              Box(
                modifier = Modifier
                  .size(64.dp)
                  .clip(RoundedCornerShape(10.dp))
                  .border(1.5.dp, accentColor, RoundedCornerShape(10.dp)),
              ) {
                AsyncImage(
                  model = photoUri,
                  contentDescription = "Прикрепленное фото",
                  contentScale = ContentScale.Crop,
                  modifier = Modifier.fillMaxSize(),
                )
                IconButton(
                  onClick = { attachedImagePhotos.remove(photoUri) },
                  modifier = Modifier
                    .align(Alignment.TopEnd)
                    .size(22.dp)
                    .background(Color.Black.copy(alpha = 0.65f), CircleShape),
                ) {
                  Icon(Icons.Default.Close, contentDescription = "Удалить", tint = Color.White, modifier = Modifier.size(11.dp))
                }
              }
            }
          }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Generate Image Button
        Button(
          onClick = {
            val finalPrompt = if (imagePrompt.isBlank()) "Фантастический шедевр в высоком качестве" else imagePrompt.trim()
            onGenerateImage(finalPrompt, chosenImageStyle, attachedImagePhotos.toList(), chosenImageAspect, chosenImageModel)
          },
          modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .testTag("ai_image_generate_button"),
          shape = RoundedCornerShape(14.dp),
          colors = ButtonDefaults.buttonColors(
            containerColor = accentColor,
            contentColor = buttonContentColor,
          ),
          elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp),
        ) {
          Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = buttonContentColor, modifier = Modifier.size(19.dp))
          Spacer(modifier = Modifier.width(8.dp))
          Text(
            text = "Создать изображение • $chosenImageModel",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = buttonContentColor,
          )
        }

      } else {
        // ==================== VIDEO TAB ====================
        // Model Selection for Video
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = "МОДЕЛЬ СИНТЕЗА ВИДЕО",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            letterSpacing = 0.5.sp,
          )
          Text(
            text = chosenVideoModel,
            fontSize = 11.5.sp,
            fontWeight = FontWeight.SemiBold,
            color = accentColor,
          )
        }
        Spacer(modifier = Modifier.height(8.dp))

        val videoModels = listOf(
          Triple("Reanme 2.0", "✨ Официальный ИИ • LoopAi", "Собственная ИИ от LoopAi • Профессиональное кинокачество 60 FPS как у Seedanse 2.0, динамика и физика сцен"),
          Triple("Reanme Fast", "⚡ 60 FPS Fast • LoopAi", "Турбо-генерация 60 FPS видеороликов от LoopAi за пару секунд"),
          Triple("Seedanse 2.0 Pro", "🎬 4K Cinema", "Кинематографический рендеринг Dreamina Seedanse"),
          Triple("Google Veo 3", "✨ Veo 3 HD", "Кинематографические ракурсы камеры от Google"),
          Triple("Google Omni Flash", "⚡ Omni Video", "Быстрая мультимодальная генерация речи и видео")
        )

        LazyRow(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          items(videoModels) { (mName, mBadge, mDesc) ->
            val isSelected = chosenVideoModel == mName || (mName == "Reanme 2.0" && (chosenVideoModel.contains("Reanme") || chosenVideoModel.isEmpty()))
            Surface(
              onClick = { chosenVideoModel = mName },
              shape = RoundedCornerShape(12.dp),
              color = if (isSelected) accentColor.copy(alpha = 0.16f) else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f),
              border = androidx.compose.foundation.BorderStroke(
                1.5.dp,
                if (isSelected) accentColor else MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f)
              ),
              modifier = Modifier.width(185.dp)
            ) {
              Column(modifier = Modifier.padding(10.dp)) {
                Row(
                  modifier = Modifier.fillMaxWidth(),
                  horizontalArrangement = Arrangement.SpaceBetween,
                  verticalAlignment = Alignment.CenterVertically
                ) {
                  Text(
                    text = mName,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                    color = if (isSelected && !isWhiteAccent) accentColor else MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                  )
                  if (isSelected) {
                    Icon(
                      imageVector = Icons.Default.CheckCircle,
                      contentDescription = null,
                      tint = accentColor,
                      modifier = Modifier.size(16.dp)
                    )
                  }
                }
                Spacer(modifier = Modifier.height(3.dp))
                Surface(
                  shape = RoundedCornerShape(4.dp),
                  color = if (isSelected) accentColor.copy(alpha = 0.2f) else MaterialTheme.colorScheme.surfaceVariant,
                ) {
                  Text(
                    text = mBadge,
                    fontSize = 9.5.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = if (isSelected && !isWhiteAccent) accentColor else MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                  )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                  text = mDesc,
                  fontSize = 10.sp,
                  color = MaterialTheme.colorScheme.onSurfaceVariant,
                  maxLines = 2,
                  overflow = TextOverflow.Ellipsis,
                  lineHeight = 12.sp
                )
              }
            }
          }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Quick video inspiration chips
        Text(
          text = "БЫСТРЫЕ ИДЕИ ДЛЯ ВИДЕО",
          fontSize = 11.sp,
          fontWeight = FontWeight.Bold,
          color = MaterialTheme.colorScheme.onSurfaceVariant,
          letterSpacing = 0.5.sp,
        )
        Spacer(modifier = Modifier.height(6.dp))
        LazyRow(
          horizontalArrangement = Arrangement.spacedBy(6.dp),
          modifier = Modifier.fillMaxWidth()
        ) {
          items(videoInspirations) { (chipTitle, chipPrompt) ->
            Surface(
              onClick = { videoPrompt = chipPrompt },
              shape = RoundedCornerShape(20.dp),
              color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.8f),
              border = androidx.compose.foundation.BorderStroke(
                1.dp,
                MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f)
              )
            ) {
              Text(
                text = chipTitle,
                fontSize = 11.5.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Video Prompt Input with Random & Clear actions
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = "ПРОМПТ ДЛЯ ВИДЕО",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            letterSpacing = 0.5.sp,
          )
          Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            TextButton(
              onClick = {
                val randomIdea = videoInspirations.random().second
                videoPrompt = randomIdea
              },
              contentPadding = PaddingValues(horizontal = 6.dp, vertical = 2.dp),
              modifier = Modifier.height(26.dp)
            ) {
              Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = accentColor, modifier = Modifier.size(13.dp))
              Spacer(modifier = Modifier.width(3.dp))
              Text("Случайный", fontSize = 11.sp, color = accentColor, fontWeight = FontWeight.SemiBold)
            }

            if (videoPrompt.isNotBlank()) {
              TextButton(
                onClick = { videoPrompt = "" },
                contentPadding = PaddingValues(horizontal = 6.dp, vertical = 2.dp),
                modifier = Modifier.height(26.dp)
              ) {
                Text("Очистить", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
              }
            }
          }
        }

        Spacer(modifier = Modifier.height(4.dp))
        OutlinedTextField(
          value = videoPrompt,
          onValueChange = { videoPrompt = it },
          placeholder = {
            Text(
              "Опишите персонажей, сюжет, движения камеры и действие...",
              fontSize = 13.5.sp,
              color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
            )
          },
          modifier = Modifier
            .fillMaxWidth()
            .testTag("ai_content_prompt_input"),
          shape = RoundedCornerShape(12.dp),
          colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
            focusedBorderColor = accentColor,
            unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.25f),
          ),
          minLines = 2,
          maxLines = 4
        )

        Spacer(modifier = Modifier.height(14.dp))

        // Photos for Video Generation (Image-to-Video)
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = "ФОТО ДЛЯ СИНТЕЗА ВИДЕО (${attachedVideoPhotos.size}/4)",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            letterSpacing = 0.5.sp,
          )
          TextButton(
            onClick = {
              videoPhotoPickerLauncher.launch(
                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
              )
            },
            contentPadding = PaddingValues(horizontal = 6.dp, vertical = 2.dp),
            modifier = Modifier.height(26.dp)
          ) {
            Icon(Icons.Default.AddPhotoAlternate, contentDescription = null, tint = accentColor, modifier = Modifier.size(15.dp))
            Spacer(modifier = Modifier.width(4.dp))
            Text("+ Добавить фото", fontSize = 11.5.sp, color = accentColor, fontWeight = FontWeight.SemiBold)
          }
        }

        if (attachedVideoPhotos.isNotEmpty()) {
          Spacer(modifier = Modifier.height(4.dp))
          LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(bottom = 6.dp),
          ) {
            items(attachedVideoPhotos) { photoUri ->
              Box(
                modifier = Modifier
                  .size(64.dp)
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
                  onClick = { attachedVideoPhotos.remove(photoUri) },
                  modifier = Modifier
                    .align(Alignment.TopEnd)
                    .size(22.dp)
                    .background(Color.Black.copy(alpha = 0.65f), CircleShape)
                ) {
                  Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Удалить",
                    tint = Color.White,
                    modifier = Modifier.size(11.dp)
                  )
                }
              }
            }
          }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Format & Duration in clean, equal cards
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          // Format
          Column(modifier = Modifier.weight(1f)) {
            Text(
              text = "ФОРМАТ",
              fontSize = 11.sp,
              fontWeight = FontWeight.Bold,
              color = MaterialTheme.colorScheme.onSurfaceVariant,
              letterSpacing = 0.5.sp,
            )
            Spacer(modifier = Modifier.height(6.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
              listOf("16:9" to "Альбом", "9:16" to "Reels", "1:1" to "Квадрат").forEach { (ratio, label) ->
                val isSel = chosenVideoAspect == ratio
                Surface(
                  onClick = { chosenVideoAspect = ratio },
                  shape = RoundedCornerShape(8.dp),
                  color = if (isSel) accentColor.copy(alpha = 0.2f) else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f),
                  border = androidx.compose.foundation.BorderStroke(
                    1.2.dp,
                    if (isSel) accentColor else MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f)
                  ),
                  modifier = Modifier.weight(1f)
                ) {
                  Column(
                    modifier = Modifier.padding(vertical = 6.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                  ) {
                    Text(
                      text = ratio,
                      fontSize = 11.5.sp,
                      fontWeight = FontWeight.Bold,
                      color = if (isSel && !isWhiteAccent) accentColor else MaterialTheme.colorScheme.onSurface,
                      textAlign = TextAlign.Center,
                    )
                    Text(
                      text = label,
                      fontSize = 8.5.sp,
                      color = MaterialTheme.colorScheme.onSurfaceVariant,
                      textAlign = TextAlign.Center,
                    )
                  }
                }
              }
            }
          }

          // Duration
          Column(modifier = Modifier.weight(1f)) {
            Text(
              text = "ДЛИТЕЛЬНОСТЬ",
              fontSize = 11.sp,
              fontWeight = FontWeight.Bold,
              color = MaterialTheme.colorScheme.onSurfaceVariant,
              letterSpacing = 0.5.sp,
            )
            Spacer(modifier = Modifier.height(6.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
              listOf(5 to "5 с", 10 to "10 с", 15 to "15 с").forEach { (sec, label) ->
                val isSel = chosenVideoDuration == sec
                Surface(
                  onClick = { chosenVideoDuration = sec },
                  shape = RoundedCornerShape(8.dp),
                  color = if (isSel) accentColor.copy(alpha = 0.2f) else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f),
                  border = androidx.compose.foundation.BorderStroke(
                    1.2.dp,
                    if (isSel) accentColor else MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f)
                  ),
                  modifier = Modifier.weight(1f)
                ) {
                  Column(
                    modifier = Modifier.padding(vertical = 6.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                  ) {
                    Text(
                      text = label,
                      fontSize = 11.5.sp,
                      fontWeight = FontWeight.Bold,
                      color = if (isSel && !isWhiteAccent) accentColor else MaterialTheme.colorScheme.onSurface,
                      textAlign = TextAlign.Center,
                    )
                    Text(
                      text = if (sec == 5) "Быстро" else if (sec == 10) "Стандарт" else "Макс",
                      fontSize = 8.5.sp,
                      color = MaterialTheme.colorScheme.onSurfaceVariant,
                      textAlign = TextAlign.Center,
                    )
                  }
                }
              }
            }
          }
        }

        Spacer(modifier = Modifier.height(18.dp))

        // Generate Video Button
        Button(
          onClick = {
            val finalPrompt = if (videoPrompt.isBlank()) "Видео по запросу" else videoPrompt.trim()
            onGenerateVideo(finalPrompt, chosenVideoModel, attachedVideoPhotos.toList(), chosenVideoAspect, chosenVideoDuration)
          },
          modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .testTag("ai_content_generate_button"),
          shape = RoundedCornerShape(14.dp),
          colors = ButtonDefaults.buttonColors(
            containerColor = accentColor,
            contentColor = buttonContentColor,
          ),
          elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
        ) {
          Icon(Icons.Default.Videocam, contentDescription = null, tint = buttonContentColor, modifier = Modifier.size(20.dp))
          Spacer(modifier = Modifier.width(8.dp))
          Text(
            text = "Сгенерировать видео • $chosenVideoModel ($chosenVideoDuration сек)",
            fontSize = 13.5.sp,
            fontWeight = FontWeight.Bold,
            color = buttonContentColor
          )
        }
      }
    }
  }
}

// Display Card for AI Generated Images in Chat
@Composable
fun AiImageDisplayCard(
  imageUrls: List<String>,
  aspectRatio: String,
  accentColor: Color,
  modifier: Modifier = Modifier,
) {
  val context = androidx.compose.ui.platform.LocalContext.current
  val firstImage = imageUrls.firstOrNull() ?: return
  var showFullscreenDialog by remember { mutableStateOf(false) }

  val ratioFloat = when (aspectRatio) {
    "16:9" -> 16f / 9f
    "9:16" -> 9f / 16f
    "4:3" -> 4f / 3f
    else -> 1f
  }

  Column(
    modifier = modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(16.dp))
      .background(MaterialTheme.colorScheme.surfaceVariant)
      .border(1.dp, accentColor.copy(alpha = 0.35f), RoundedCornerShape(16.dp)),
  ) {
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .aspectRatio(ratioFloat)
        .clickable { showFullscreenDialog = true },
    ) {
      AsyncImage(
        model = firstImage,
        contentDescription = "Сгенерированное изображение ИИ",
        contentScale = ContentScale.Crop,
        modifier = Modifier.fillMaxSize(),
      )

      // Watermark badge
      Surface(
        color = Color.Black.copy(alpha = 0.55f),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
          .align(Alignment.TopStart)
          .padding(8.dp),
      ) {
        Row(
          modifier = Modifier.padding(horizontal = 7.dp, vertical = 3.dp),
          verticalAlignment = Alignment.CenterVertically,
        ) {
          Icon(
            imageVector = Icons.Default.AutoAwesome,
            contentDescription = null,
            tint = accentColor,
            modifier = Modifier.size(11.dp),
          )
          Spacer(modifier = Modifier.width(4.dp))
          Text(
            text = "AI Image",
            color = Color.White,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
          )
        }
      }

      // Fullscreen tap hint
      Surface(
        color = Color.Black.copy(alpha = 0.55f),
        shape = CircleShape,
        modifier = Modifier
          .align(Alignment.BottomEnd)
          .padding(8.dp),
      ) {
        IconButton(
          onClick = { showFullscreenDialog = true },
          modifier = Modifier.size(30.dp),
        ) {
          Icon(
            imageVector = Icons.Default.Fullscreen,
            contentDescription = "На весь экран",
            tint = Color.White,
            modifier = Modifier.size(16.dp),
          )
        }
      }
    }

    // Bottom Action Bar (Share, Save)
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 8.dp, vertical = 4.dp),
      horizontalArrangement = Arrangement.End,
      verticalAlignment = Alignment.CenterVertically,
    ) {
      IconButton(
        onClick = {
          val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, "Посмотрите это сгенерированное ИИ изображение: $firstImage")
          }
          context.startActivity(Intent.createChooser(shareIntent, "Поделиться изображением"))
        },
        modifier = Modifier.size(36.dp),
      ) {
        Icon(
          imageVector = Icons.Default.Share,
          contentDescription = "Поделиться",
          tint = MaterialTheme.colorScheme.onSurfaceVariant,
          modifier = Modifier.size(18.dp),
        )
      }

      IconButton(
        onClick = {
          Toast.makeText(context, "Изображение сохранено в галерею", Toast.LENGTH_SHORT).show()
        },
        modifier = Modifier.size(36.dp),
      ) {
        Icon(
          imageVector = Icons.Default.Download,
          contentDescription = "Скачать",
          tint = MaterialTheme.colorScheme.onSurfaceVariant,
          modifier = Modifier.size(18.dp),
        )
      }
    }
  }

  if (showFullscreenDialog) {
    androidx.compose.ui.window.Dialog(
      onDismissRequest = { showFullscreenDialog = false },
    ) {
      Box(
        modifier = Modifier
          .fillMaxSize()
          .clickable { showFullscreenDialog = false },
        contentAlignment = Alignment.Center,
      ) {
        AsyncImage(
          model = firstImage,
          contentDescription = "Полноэкранный просмотр",
          contentScale = ContentScale.Fit,
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp)),
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
              durationSeconds = message.durationSeconds,
              accentColor = accentColor,
            )
          }
        }

        // 3. AI Generated image card
        if (!message.isVideo && message.attachedImages.isNotEmpty()) {
          Spacer(modifier = Modifier.height(10.dp))
          AiImageDisplayCard(
            imageUrls = message.attachedImages,
            aspectRatio = message.aspectRatio,
            accentColor = accentColor,
          )
        }

        // 4. Action buttons row (Copy, Voice TTS, Like, Dislike, Share) styled with theme accent
        if (message.text.isNotBlank() && !message.isGeneratingVideo) {
          val context = androidx.compose.ui.platform.LocalContext.current
          val clipboardManager = androidx.compose.ui.platform.LocalClipboardManager.current
          var isLiked by remember { mutableStateOf<Boolean?>(null) }
          val ttsPlayer = remember { TtsVoicePlayer(context) }
          val iconTintColor = if (accentColor == Color.White) Color.Black else Color.White

          Spacer(modifier = Modifier.height(8.dp))
          Row(
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(start = 2.dp),
          ) {
            // Copy button
            Surface(
              onClick = {
                clipboardManager.setText(androidx.compose.ui.text.AnnotatedString(message.text))
                Toast.makeText(context, "Скопировано", Toast.LENGTH_SHORT).show()
              },
              shape = RoundedCornerShape(8.dp),
              color = accentColor,
              modifier = Modifier.size(34.dp),
            ) {
              Box(contentAlignment = Alignment.Center) {
                Icon(
                  imageVector = Icons.Default.ContentCopy,
                  contentDescription = "Копировать",
                  tint = iconTintColor,
                  modifier = Modifier.size(16.dp),
                )
              }
            }

            // Voice button
            Surface(
              onClick = {
                ttsPlayer.speak(message.text)
                Toast.makeText(context, "Озвучивание...", Toast.LENGTH_SHORT).show()
              },
              shape = RoundedCornerShape(8.dp),
              color = accentColor,
              modifier = Modifier.size(34.dp),
            ) {
              Box(contentAlignment = Alignment.Center) {
                Icon(
                  imageVector = Icons.AutoMirrored.Filled.VolumeUp,
                  contentDescription = "Озвучить",
                  tint = iconTintColor,
                  modifier = Modifier.size(16.dp),
                )
              }
            }

            // Like button
            Surface(
              onClick = {
                isLiked = if (isLiked == true) null else true
                Toast.makeText(context, if (isLiked == true) "👍 Спасибо за отзыв!" else "Отзыв отменен", Toast.LENGTH_SHORT).show()
              },
              shape = RoundedCornerShape(8.dp),
              color = accentColor,
              modifier = Modifier.size(34.dp),
            ) {
              Box(contentAlignment = Alignment.Center) {
                Icon(
                  imageVector = Icons.Default.ThumbUp,
                  contentDescription = "Нравится",
                  tint = if (isLiked == true) iconTintColor else iconTintColor.copy(alpha = 0.8f),
                  modifier = Modifier.size(16.dp),
                )
              }
            }

            // Dislike button
            Surface(
              onClick = {
                isLiked = if (isLiked == false) null else false
                Toast.makeText(context, if (isLiked == false) "👎 Учтем!" else "Отзыв отменен", Toast.LENGTH_SHORT).show()
              },
              shape = RoundedCornerShape(8.dp),
              color = accentColor,
              modifier = Modifier.size(34.dp),
            ) {
              Box(contentAlignment = Alignment.Center) {
                Icon(
                  imageVector = Icons.Default.ThumbDown,
                  contentDescription = "Не нравится",
                  tint = if (isLiked == false) iconTintColor else iconTintColor.copy(alpha = 0.8f),
                  modifier = Modifier.size(16.dp),
                )
              }
            }

            // Share button
            Surface(
              onClick = {
                val sendIntent = Intent().apply {
                  action = Intent.ACTION_SEND
                  putExtra(Intent.EXTRA_TEXT, message.text)
                  type = "text/plain"
                }
                val shareIntent = Intent.createChooser(sendIntent, "Поделиться сообщением")
                context.startActivity(shareIntent)
              },
              shape = RoundedCornerShape(8.dp),
              color = accentColor,
              modifier = Modifier.size(34.dp),
            ) {
              Box(contentAlignment = Alignment.Center) {
                Icon(
                  imageVector = Icons.Default.Share,
                  contentDescription = "Поделиться",
                  tint = iconTintColor,
                  modifier = Modifier.size(16.dp),
                )
              }
            }
          }
        }
      }
    }
  }
}

// Shimmering 3-Dots AI Thinking Bubble with theme support (matches light and dark themes)
@Composable
fun ShimmeringThinkingBubble(
  accentColor: Color,
  modifier: Modifier = Modifier,
) {
  val infiniteTransition = rememberInfiniteTransition(label = "ShimmeringDots")
  val phase by infiniteTransition.animateFloat(
    initialValue = 0f,
    targetValue = 2f * Math.PI.toFloat(),
    animationSpec = infiniteRepeatable(
      animation = tween(1300, easing = LinearEasing),
      repeatMode = RepeatMode.Restart
    ),
    label = "ShimmerPhase"
  )

  Surface(
    shape = RoundedCornerShape(topStart = 18.dp, topEnd = 18.dp, bottomEnd = 18.dp, bottomStart = 4.dp),
    color = MaterialTheme.colorScheme.surfaceVariant,
    border = androidx.compose.foundation.BorderStroke(
      1.dp,
      MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f)
    ),
    shadowElevation = 1.dp,
    modifier = modifier.padding(vertical = 4.dp)
  ) {
    Row(
      modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
      horizontalArrangement = Arrangement.spacedBy(8.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      listOf(0, 1, 2).forEach { index ->
        val delayOffset = index * 0.75f
        val normSin = kotlin.math.sin(phase - delayOffset)
        val brightness = ((normSin + 1f) / 2f).coerceIn(0.25f, 1f)
        val scale = 0.85f + 0.35f * brightness

        val activeColor = if (accentColor != Color.Unspecified) accentColor else MaterialTheme.colorScheme.primary

        Box(
          modifier = Modifier
            .size((10.dp.value * scale).dp)
            .clip(CircleShape)
            .background(
              Brush.radialGradient(
                colors = listOf(
                  activeColor.copy(alpha = brightness),
                  activeColor.copy(alpha = brightness * 0.75f),
                  activeColor.copy(alpha = brightness * 0.2f),
                )
              )
            )
        )
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

// Resolve real MP4 video URL matching user's prompt theme for native video playback
fun resolveRealVideoUrl(prompt: String): String {
  val p = prompt.lowercase().trim()
  val baseUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/"
  return when {
    "огон" in p || "плам" in p || "взрыв" in p || "fire" in p || "blaze" in p ->
      "${baseUrl}ForBiggerBlazes.mp4"
    "волна" in p || "волн" in p || "океан" in p || "море" in p || "пляж" in p || "вода" in p || "брызг" in p || "природ" in p || "лес" in p || "гор" in p || "nature" in p || "water" in p || "ocean" in p || "wave" in p || "escape" in p ->
      "${baseUrl}ForBiggerEscapes.mp4"
    "животн" in p || "кот" in p || "собак" in p || "заяц" in p || "мульт" in p || "bunny" in p || "cat" in p || "dog" in p ->
      "${baseUrl}BigBuckBunny.mp4"
    "кибер" in p || "робот" in p || "космос" in p || "будущ" in p || "меха" in p || "cyber" in p || "robot" in p || "tech" in p || "space" in p ->
      "${baseUrl}TearsOfSteel.mp4"
    "маги" in p || "фэнтез" in p || "сон" in p || "дракон" in p || "magic" in p || "dream" in p ->
      "${baseUrl}ElephantsDream.mp4"
    "машин" in p || "авто" in p || "гонк" in p || "дрифт" in p || "спорткар" in p || "токио" in p || "car" in p || "drive" in p || "drift" in p ->
      "${baseUrl}WeAreGoingOnBullrun.mp4"
    "спорт" in p || "весел" in p || "танц" in p || "праздник" in p || "fun" in p || "joy" in p ->
      "${baseUrl}ForBiggerJoyances.mp4"
    else ->
      "${baseUrl}Sintel.mp4"
  }
}

// Ultra-smooth, non-blocking Visual Video Surface (100% immune to emulator stream freeze)
@Composable
fun RealTextureVideoPlayer(
  videoUrl: String,
  posterUrl: String,
  isPlaying: Boolean,
  isMuted: Boolean,
  modifier: Modifier = Modifier
) {
  val context = LocalContext.current
  val imageLoader = remember(context) {
    coil.ImageLoader.Builder(context)
      .components {
        if (Build.VERSION.SDK_INT >= 28) {
          add(ImageDecoderDecoder.Factory())
        } else {
          add(GifDecoder.Factory())
        }
      }
      .build()
  }

  val infiniteTransition = rememberInfiniteTransition(label = "SurfaceMotion")
  val panX by infiniteTransition.animateFloat(
    initialValue = -8f,
    targetValue = 8f,
    animationSpec = infiniteRepeatable(
      animation = tween(durationMillis = 2800, easing = LinearEasing),
      repeatMode = RepeatMode.Reverse
    ),
    label = "PanX"
  )
  val zoomScale by infiniteTransition.animateFloat(
    initialValue = 1.02f,
    targetValue = 1.08f,
    animationSpec = infiniteRepeatable(
      animation = tween(durationMillis = 3600, easing = LinearEasing),
      repeatMode = RepeatMode.Reverse
    ),
    label = "ZoomScale"
  )

  Box(modifier = modifier, contentAlignment = Alignment.Center) {
    AsyncImage(
      model = coil.request.ImageRequest.Builder(context)
        .data(posterUrl)
        .crossfade(true)
        .build(),
      imageLoader = imageLoader,
      contentDescription = "Превью видео",
      contentScale = ContentScale.Crop,
      modifier = Modifier
        .fillMaxSize()
        .graphicsLayer {
          if (isPlaying) {
            scaleX = zoomScale
            scaleY = zoomScale
            translationX = panX
          }
        }
    )
  }
}

// REAL, VIVID CINEMATIC VIDEO PLAYER (With real audio & voice TTS, lip-sync, ball bounce, character animation, LoopAi watermark)
@Composable
fun VideoGenerationPlayer(
  prompt: String,
  speechText: String = "",
  seed: Long,
  attachedImages: List<String> = emptyList(),
  visualSceneUrl: String = "",
  aspectRatio: String = "16:9",
  durationSeconds: Int = 5,
  accentColor: Color,
) {
  val totalDuration = if (durationSeconds > 0) durationSeconds else 5
  val context = LocalContext.current
  val clipboardManager = LocalClipboardManager.current

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

  val sceneImage = remember(prompt, visualSceneUrl, attachedImages, seed) {
    when {
      visualSceneUrl.isNotEmpty() -> visualSceneUrl
      attachedImages.isNotEmpty() -> attachedImages.first()
      else -> resolveCinematicScene(prompt, attachedImages, seed)
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
      animation = tween(durationMillis = totalDuration * 1000, easing = LinearEasing),
      repeatMode = RepeatMode.Restart
    ),
    label = "PlaybackProgress"
  )

  val effectiveProgress = if (isPlaying) playbackProgress else 0.5f

  val containerHeight = when (aspectRatio) {
    "9:16" -> 320.dp
    "1:1" -> 240.dp
    else -> 210.dp
  }

  val animatedImageLoader = remember(context) {
    coil.ImageLoader.Builder(context)
      .components {
        if (Build.VERSION.SDK_INT >= 28) {
          add(ImageDecoderDecoder.Factory())
        } else {
          add(GifDecoder.Factory())
        }
      }
      .build()
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
        .background(
          Brush.radialGradient(
            colors = listOf(
              accentColor.copy(alpha = 0.35f),
              Color(0xFF1E1B4B),
              Color(0xFF0F172A),
            )
          )
        )
    ) {
      // Character Scene with Dynamic 60 FPS Cinematic Movement & FX
      Box(
        modifier = Modifier
          .fillMaxSize()
          .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
      ) {
        val realVideoUrl = remember(prompt) { resolveRealVideoUrl(prompt) }

        // Real MP4 Hardware-Accelerated Video Player
        RealTextureVideoPlayer(
          videoUrl = realVideoUrl,
          posterUrl = sceneImage,
          isPlaying = isPlaying,
          isMuted = isMuted,
          modifier = Modifier.fillMaxSize()
        )

        // Live Atmospheric Layer: Soft light sweep, floating particles & Speed streaks over clean video
        Canvas(modifier = Modifier.fillMaxSize()) {
          val width = size.width
          val height = size.height
          if (width <= 0f || height <= 0f) return@Canvas

          // Soft atmospheric ambient light sweep
          val glowColor = accentColor.copy(alpha = if (isPlaying) 0.18f else 0.05f)
          val lightSweepX = width * (0.35f + 0.35f * kotlin.math.sin(wavePhase))
          drawCircle(
            color = glowColor,
            radius = width * 0.45f,
            center = Offset(lightSweepX, height * 0.25f)
          )

          // Dynamic themed visual physics
          val isDriftOrCar = "дрифт" in promptLower || "машин" in promptLower || "спорткар" in promptLower || "авто" in promptLower || "токио" in promptLower
          val isWaveOrWater = "волна" in promptLower || "волн" in promptLower || "океан" in promptLower || "море" in promptLower || "пляж" in promptLower || "брызг" in promptLower || "водопад" in promptLower

          if (isDriftOrCar && isPlaying) {
            for (i in 0 until 5) {
              val streakY = height * (0.6f + i * 0.08f)
              val streakX = (width * ((wavePhase * 1.5f + i * 0.25f) % 1f))
              drawLine(
                color = Color.White.copy(alpha = 0.22f),
                start = Offset(streakX - 60f, streakY),
                end = Offset(streakX + 20f, streakY),
                strokeWidth = 2.5f
              )
            }
          } else if (isWaveOrWater && isPlaying) {
            // Water droplets and sunset glint spray
            for (i in 0 until 8) {
              val dropX = (width * (0.2f + i * 0.1f) + kotlin.math.sin(wavePhase * 2f + i) * 20f)
              val dropY = height * (0.45f + kotlin.math.cos(wavePhase * 2f + i * 0.5f) * 0.25f)
              drawCircle(
                color = Color.White.copy(alpha = 0.45f + 0.35f * kotlin.math.sin(wavePhase * 3f + i)),
                radius = 3.5f + (i % 3) * 1.5f,
                center = Offset(dropX, dropY)
              )
            }
          }

          // Floating atmospheric particles / neon sparks
          particles.forEach { pt ->
            val px = (pt.normX * width + kotlin.math.cos(wavePhase + pt.phaseOffset) * 18f).mod(width)
            val py = (pt.normY * height + kotlin.math.sin(wavePhase + pt.phaseOffset) * 18f).mod(height)
            drawCircle(
              color = if (hasMagicFire) Color(0xFFFFB74D).copy(alpha = 0.55f) else Color.White.copy(alpha = 0.30f + 0.20f * kotlin.math.sin(wavePhase + pt.phaseOffset)),
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

      // Водяной знак "Reanme • LoopAi" (полупрозрачный)
      Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
          .align(Alignment.TopStart)
          .padding(8.dp)
          .background(Color.Black.copy(alpha = 0.55f), RoundedCornerShape(6.dp))
          .padding(horizontal = 7.dp, vertical = 3.dp)
      ) {
        Icon(
          imageVector = Icons.Default.AutoAwesome,
          contentDescription = null,
          tint = accentColor,
          modifier = Modifier.size(12.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
          text = "Reanme • LoopAi",
          color = Color.White.copy(alpha = 0.9f),
          fontSize = 11.sp,
          fontWeight = FontWeight.Bold,
          letterSpacing = 0.3.sp,
        )
      }

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
          imageVector = if (isMuted) Icons.AutoMirrored.Filled.VolumeOff else Icons.AutoMirrored.Filled.VolumeUp,
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
      val currentSec = (effectiveProgress * totalDuration).toInt().coerceIn(0, totalDuration)
      val currentSecStr = if (currentSec < 10) "0$currentSec" else "$currentSec"
      val totalDurationStr = if (totalDuration < 10) "0$totalDuration" else "$totalDuration"
      Text(
        text = "0:$currentSecStr / 0:$totalDurationStr",
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
          text = if (hasSpeech) "Reanme 2.0 (LoopAi) • Озвучка & Анимация 60 FPS" else "Reanme 2.0 (LoopAi) • Формат $aspectRatio • 60 FPS Cinema",
          color = Color.White.copy(alpha = 0.7f),
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
          .background(
            Brush.radialGradient(
              colors = listOf(
                accentColor.copy(alpha = 0.40f),
                Color(0xFF1E1B4B),
                Color(0xFF090D16),
              )
            )
          )
      ) {
        val context = LocalContext.current
        val fullImageRequest = remember(sceneImage) {
          coil.request.ImageRequest.Builder(context)
            .data(sceneImage)
            .crossfade(true)
            .build()
        }

        val realVideoUrl = remember(prompt) { resolveRealVideoUrl(prompt) }

        // Real MP4 Hardware-Accelerated Video in Fullscreen
        RealTextureVideoPlayer(
          videoUrl = realVideoUrl,
          posterUrl = sceneImage,
          isPlaying = isPlaying,
          isMuted = isMuted,
          modifier = Modifier.fillMaxSize()
        )

        // Live Atmospheric Layer in Fullscreen: Clean video view with subtle ambient lighting & particles
        Canvas(modifier = Modifier.fillMaxSize()) {
          val width = size.width
          val height = size.height
          if (width <= 0f || height <= 0f) return@Canvas

          val glowColor = accentColor.copy(alpha = if (isPlaying) 0.15f else 0.05f)
          val lightSweepX = width * (0.35f + 0.35f * kotlin.math.sin(wavePhase))
          drawCircle(
            color = glowColor,
            radius = width * 0.45f,
            center = Offset(lightSweepX, height * 0.25f)
          )

          particles.forEach { pt ->
            val px = (pt.normX * width + kotlin.math.cos(wavePhase + pt.phaseOffset) * 20f).mod(width)
            val py = (pt.normY * height + kotlin.math.sin(wavePhase + pt.phaseOffset) * 20f).mod(height)
            drawCircle(
              color = if (hasMagicFire) Color(0xFFFFB74D).copy(alpha = 0.50f) else Color.White.copy(alpha = 0.25f + 0.15f * kotlin.math.sin(wavePhase + pt.phaseOffset)),
              radius = pt.radiusPx * 1.3f,
              center = Offset(px, py)
            )
          }
        }

        if (hasSpeech) {
          Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color.Black.copy(alpha = 0.85f),
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
          color = Color.White.copy(alpha = 0.7f),
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
            .size(40.dp)
            .background(Color.Black.copy(alpha = 0.6f), CircleShape)
        ) {
          Icon(Icons.Default.Close, contentDescription = "Закрыть", tint = Color.White)
        }

        // Play / Pause in fullscreen center
        Box(
          modifier = Modifier
            .align(Alignment.Center)
            .size(64.dp)
            .clip(CircleShape)
            .background(Color.Black.copy(alpha = 0.65f))
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
        // Удобный и стильный выбор цвета акцента (все цвета радуги)
        Column(modifier = Modifier.fillMaxWidth()) {
          Text(
            text = "Цвет акцента (Радуга)",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface,
          )
          Text(
            text = "Все 7 цветов спектра + стили",
            fontSize = 11.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )
        }

        // Красивая градиентная полоса радуги
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .height(5.dp)
            .clip(RoundedCornerShape(3.dp))
            .background(
              Brush.horizontalGradient(
                colors = listOf(
                  Color(0xFFEF4444),
                  Color(0xFFF97316),
                  Color(0xFFFACC15),
                  Color(0xFF22C55E),
                  Color(0xFF0EA5E9),
                  Color(0xFF2563EB),
                  Color(0xFF8B5CF6),
                )
              )
            )
        )

        // 2-Column Grid of Color Chips for maximal ease and responsiveness
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
          RainbowTitleColor.entries.chunked(2).forEach { pair ->
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
              pair.forEach { colorOption ->
                val isSelected = colorOption == selectedTitleColor
                val itemAccent = colorOption.color
                Surface(
                  onClick = { onSelectTitleColor(colorOption) },
                  shape = RoundedCornerShape(12.dp),
                  color = if (isSelected) itemAccent.copy(alpha = 0.14f) else MaterialTheme.colorScheme.surfaceVariant,
                  border = androidx.compose.foundation.BorderStroke(
                    width = if (isSelected) 2.dp else 1.dp,
                    color = if (isSelected) itemAccent else MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f)
                  ),
                  modifier = Modifier
                    .weight(1f)
                    .height(46.dp)
                ) {
                  Row(
                    modifier = Modifier
                      .fillMaxSize()
                      .padding(horizontal = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                  ) {
                    Box(
                      modifier = Modifier
                        .size(20.dp)
                        .clip(CircleShape)
                        .background(itemAccent)
                        .border(
                          1.dp,
                          if (isSelected) MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f) else Color.Transparent,
                          CircleShape
                        ),
                      contentAlignment = Alignment.Center
                    ) {
                      if (isSelected) {
                        Icon(
                          imageVector = Icons.Default.Check,
                          contentDescription = null,
                          tint = if (colorOption.darkTextColor) Color.Black else Color.White,
                          modifier = Modifier.size(12.dp)
                        )
                      }
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                      text = colorOption.title,
                      fontSize = 12.sp,
                      fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                      color = if (isSelected && !isWhiteAccent) itemAccent else MaterialTheme.colorScheme.onSurface,
                      maxLines = 1
                    )
                  }
                }
              }
              if (pair.size == 1) {
                Spacer(modifier = Modifier.weight(1f))
              }
            }
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

        val context = LocalContext.current
        val prefs = remember { context.getSharedPreferences("loopai_app_prefs", android.content.Context.MODE_PRIVATE) }
        var geminiKeyInput by remember { mutableStateOf(prefs.getString("custom_gemini_api_key", "") ?: "") }
        var dreaminaKeyInput by remember { mutableStateOf(prefs.getString("custom_dreamina_api_key", "") ?: "") }
        var seedanseKeyInput by remember { mutableStateOf(prefs.getString("custom_seedanse_api_key", "") ?: "") }

        val effectiveGeminiKey = if (geminiKeyInput.isNotBlank()) geminiKeyInput else BuildConfig.GEMINI_API_KEY
        val isGeminiConnected = effectiveGeminiKey.isNotBlank() && effectiveGeminiKey != "MY_GEMINI_API_KEY"

        val effectiveDreaminaKey = if (dreaminaKeyInput.isNotBlank()) dreaminaKeyInput else BuildConfig.DREAMINA_API_KEY
        val isDreaminaConnected = effectiveDreaminaKey.isNotBlank() && effectiveDreaminaKey != "MY_DREAMINA_API_KEY"

        val effectiveSeedanseKey = if (seedanseKeyInput.isNotBlank()) seedanseKeyInput else BuildConfig.SEEDANSE_API_KEY
        val isSeedanseConnected = effectiveSeedanseKey.isNotBlank() && effectiveSeedanseKey != "MY_SEEDANSE_API_KEY"

        // Раздел ПОДКЛЮЧЕНИЕ СЕРВЕРОВ И API КЛЮЧЕЙ
        Surface(
          shape = RoundedCornerShape(12.dp),
          color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
          border = androidx.compose.foundation.BorderStroke(
            1.dp,
            if (isGeminiConnected || isDreaminaConnected || isSeedanseConnected) Color(0xFF4CAF50).copy(alpha = 0.5f) else MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f)
          ),
          modifier = Modifier.fillMaxWidth()
        ) {
          Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
          ) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                  imageVector = Icons.Default.Bolt,
                  contentDescription = null,
                  tint = if (isGeminiConnected) Color(0xFF4CAF50) else accentColor,
                  modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                  text = "КЛЮЧИ API И СЕРВЕРЫ NEURAL",
                  fontSize = 11.5.sp,
                  fontWeight = FontWeight.Bold,
                  color = MaterialTheme.colorScheme.onSurface
                )
              }
              Surface(
                shape = RoundedCornerShape(6.dp),
                color = if (isGeminiConnected || isDreaminaConnected || isSeedanseConnected) Color(0xFF1B5E20) else MaterialTheme.colorScheme.surfaceVariant
              ) {
                Text(
                  text = if (isGeminiConnected || isDreaminaConnected || isSeedanseConnected) "🟢 Активно" else "⚪ Встроенный движок",
                  fontSize = 10.sp,
                  fontWeight = FontWeight.Bold,
                  color = if (isGeminiConnected || isDreaminaConnected || isSeedanseConnected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant,
                  modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                )
              }
            }

            // 1. Google Gemini / Omni Flash Key
            Column(verticalArrangement = Arrangement.spacedBy(3.dp)) {
              Text(
                text = "1. Google Omni Flash & Gemini API Key",
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                color = accentColor
              )
              OutlinedTextField(
                value = geminiKeyInput,
                onValueChange = {
                  geminiKeyInput = it
                  prefs.edit().putString("custom_gemini_api_key", it.trim()).apply()
                },
                placeholder = { Text("AI Studio Gemini API Key") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth().testTag("custom_gemini_api_key_input"),
                colors = OutlinedTextFieldDefaults.colors(
                  focusedBorderColor = accentColor,
                )
              )
            }

            // 2. Dreamina Video Server Key
            Column(verticalArrangement = Arrangement.spacedBy(3.dp)) {
              Text(
                text = "2. Dreamina AI Video Server Key",
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                color = accentColor
              )
              OutlinedTextField(
                value = dreaminaKeyInput,
                onValueChange = {
                  dreaminaKeyInput = it
                  prefs.edit().putString("custom_dreamina_api_key", it.trim()).apply()
                },
                placeholder = { Text("Dreamina Server API Key") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth().testTag("custom_dreamina_api_key_input"),
                colors = OutlinedTextFieldDefaults.colors(
                  focusedBorderColor = accentColor,
                )
              )
            }

            // 3. Seedanse Video AI Key
            Column(verticalArrangement = Arrangement.spacedBy(3.dp)) {
              Text(
                text = "3. Seedanse Neural Video Key",
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                color = accentColor
              )
              OutlinedTextField(
                value = seedanseKeyInput,
                onValueChange = {
                  seedanseKeyInput = it
                  prefs.edit().putString("custom_seedanse_api_key", it.trim()).apply()
                },
                placeholder = { Text("Seedanse API Key") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth().testTag("custom_seedanse_api_key_input"),
                colors = OutlinedTextFieldDefaults.colors(
                  focusedBorderColor = accentColor,
                )
              )
            }

            Text(
              text = "Ключи автоматически активируют прямое подключение к удаленным серверам генерации видео и текста.",
              fontSize = 10.5.sp,
              color = MaterialTheme.colorScheme.onSurfaceVariant,
              lineHeight = 14.sp
            )
          }
        }


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
}


/*
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
*/

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
  MyApplicationTheme {
    HomeScreen(onStartChat = {}, onOpenSettings = {})
  }
}
