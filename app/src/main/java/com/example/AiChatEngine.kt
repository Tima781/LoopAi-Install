package com.example

import kotlin.math.*

object AiChatEngine {

  fun generateResponse(userPrompt: String, model: AiModelType, mode: ModelMode): String {
    val trimmed = userPrompt.trim()
    val clean = trimmed.lowercase()

    // 1. Check for Math and Calculations
    val mathResult = trySolveMath(trimmed)
    if (mathResult != null) {
      return formatMathResponse(userPrompt, mathResult, model, mode)
    }

    // 2. Check for Code and Programming requests
    val codeResult = tryGenerateCode(clean)
    if (codeResult != null) {
      return formatCodeResponse(userPrompt, codeResult, model, mode)
    }

    // 3. Check for Science / Facts / Geography / Knowledge
    val factResult = tryAnswerFact(clean)
    if (factResult != null) {
      return formatFactResponse(userPrompt, factResult, model, mode)
    }

    // 4. Conversational and general responses
    return formatGeneralResponse(trimmed, clean, model, mode)
  }

  private fun formatMathResponse(
    originalPrompt: String,
    mathResult: MathResult,
    model: AiModelType,
    mode: ModelMode
  ): String {
    val modePrefix = if (mode == ModelMode.PRO) "🔬 [Глубокий анализ • Pro]:\n\n" else ""

    return when (model) {
      AiModelType.DEEPSEEK -> {
        val think = if (mode == ModelMode.PRO) {
          """
          |💭 <think>
          |1. Получен математический запрос: «$originalPrompt».
          |2. Извлекаем выражение: ${mathResult.expression}.
          |3. Проверяем порядок операций и вычисляем:
          |   ${mathResult.steps.joinToString("\n   ")}
          |4. Получен итоговый результат: ${mathResult.answer}.
          |</think>
          |
          """.trimMargin()
        } else ""

        """
        |${modePrefix}${think}### Ответ: **${mathResult.answer}**
        |
        |**Пошаговое решение:**
        |${mathResult.steps.joinToString("\n")}
        |
        |**${mathResult.expression} = ${mathResult.answer}**
        """.trimMargin()
      }
      AiModelType.GEMINI -> {
        """
        |${modePrefix}**${mathResult.expression} = ${mathResult.answer}**
        |
        |💡 **Решение:**
        |${mathResult.steps.joinToString("\n")}
        |
        |Результат: **${mathResult.answer}**
        """.trimMargin()
      }
      AiModelType.GOOGLE_OMNI_FLASH -> {
        """
        |⚡ **Google Omni Flash [Мультимодальный синтез]**
        |
        |$$\mathbf{${mathResult.expression} = ${mathResult.answer}}$$
        |
        |📊 **Быстрое вычисление:**
        |${mathResult.steps.joinToString("\n")}
        |
        |🔊 Синтезирована аудио-вербализация результата: *«${mathResult.expression} равно ${mathResult.answer}»*.
        """.trimMargin()
      }
      AiModelType.VEO_3 -> {
        """
        |✨ **Google Veo 3 Engine**
        |
        |🎬 Математический расчет для сцены: **${mathResult.expression} = ${mathResult.answer}**
        |
        |Параметры рендеринга синхронизированы на кадре: ${mathResult.answer}.
        """.trimMargin()
      }
      AiModelType.CHATGPT -> {
        """
        |${modePrefix}Результат вычисления **${mathResult.expression}**:
        |
        |**Ответ: ${mathResult.answer}**
        |
        |**Ход решения:**
        |${mathResult.steps.joinToString("\n")}
        """.trimMargin()
      }
      AiModelType.CLAUDE -> {
        """
        |${modePrefix}Вычисляем значение выражения **${mathResult.expression}**:
        |
        |${mathResult.steps.joinToString("\n")}
        |
        |📌 **Итоговый результат:** `${mathResult.answer}`
        """.trimMargin()
      }
      AiModelType.SEEDANSE -> {
        "Результат: **${mathResult.answer}**"
      }
    }
  }

  private fun formatCodeResponse(
    originalPrompt: String,
    codeResult: CodeSnippet,
    model: AiModelType,
    mode: ModelMode
  ): String {
    val modePrefix = if (mode == ModelMode.PRO) "🔬 [Глубокий анализ • Pro]:\n\n" else ""

    val think = if (model == AiModelType.DEEPSEEK && mode == ModelMode.PRO) {
      """
      |💭 <think>
      |1. Запрос на разработку/код: «$originalPrompt».
      |2. Определен язык и задача: ${codeResult.language} — ${codeResult.title}.
      |3. Составляем чистый, оптимизированный и идиоматичный код с комментариями.
      |4. Добавляем краткое пояснение принципа работы.
      |</think>
      |
      """.trimMargin()
    } else ""

    return """
    |${modePrefix}${think}### ${codeResult.title}
    |
    |```${codeResult.language}
    |${codeResult.code}
    |```
    |
    |💡 **Пояснение:**
    |${codeResult.explanation}
    """.trimMargin()
  }

  private fun formatFactResponse(
    originalPrompt: String,
    fact: String,
    model: AiModelType,
    mode: ModelMode
  ): String {
    val modePrefix = if (mode == ModelMode.PRO) "🔬 [Глубокий анализ • Pro]:\n\n" else ""

    val think = if (model == AiModelType.DEEPSEEK && mode == ModelMode.PRO) {
      """
      |💭 <think>
      |1. Запрос знаний: «$originalPrompt».
      |2. Извлекаем подтверждённые научные/энциклопедические данные.
      |3. Структурируем ответ для максимальной ясности.
      |</think>
      |
      """.trimMargin()
    } else ""

    return "${modePrefix}${think}${fact}"
  }

  private fun formatGeneralResponse(
    originalPrompt: String,
    clean: String,
    model: AiModelType,
    mode: ModelMode
  ): String {
    val modePrefix = if (mode == ModelMode.PRO) "🔬 [Глубокий анализ • Pro]:\n\n" else ""

    val isGreeting = clean in listOf("привет", "здравствуй", "здравствуйте", "ку", "хай", "hello", "hi", "салам", "добрый день", "добрый вечер", "доброе утро")
    val isWhoAreYou = "кто ты" in clean || "ты кто" in clean || "как тебя зовут" in clean || "что ты умеешь" in clean || "что умеешь" in clean
    val isHowAreYou = "как дела" in clean || "как ты" in clean || "что делаешь" in clean || "как поживаешь" in clean
    val isThanks = "спасибо" in clean || "благодарю" in clean || "спс" in clean || "от души" in clean

    return when (model) {
      AiModelType.DEEPSEEK -> {
        val think = if (mode == ModelMode.PRO) {
          """
          |💭 <think>
          |1. Анализирую запрос пользователя: «$originalPrompt».
          |2. Формулирую структурированный, логичный и максимально информативный ответ.
          |</think>
          |
          """.trimMargin()
        } else ""

        when {
          isGreeting -> "${modePrefix}${think}Приветствую! Я **DeepSeek R1**. Готов помочь вам с математикой, программированием, анализом данных, логическими задачами и созданием контента. Задайте любой вопрос или задачу!"
          isWhoAreYou -> "${modePrefix}${think}Я **DeepSeek** — передовая языковая модель искусственного интеллекта. Специализируюсь на:\n• 🧮 Точных математических расчётах и рассуждениях\n• 💻 Написании и отладке кода на 50+ языках\n• 🔬 Глубоком логическом анализе сложных задач\n• 📝 Работе с текстами любой сложности"
          isHowAreYou -> "${modePrefix}${think}Все нейросетевые кластеры работают на 100% мощности. Готов к вычислениям и анализу! Что будем решать?"
          isThanks -> "${modePrefix}${think}Всегда рад помочь! Если появятся новые вопросы или задачи — обращайтесь в любой момент."
          else -> "${modePrefix}${think}По поводу вашего запроса **«$originalPrompt»**:\n\nГотов предоставить детальный разбор. Уточните детали задачи или направление, и я предложу оптимальное решение шаг за шагом."
        }
      }
      AiModelType.GEMINI -> {
        when {
          isGreeting -> "${modePrefix}Привет! Я **Gemini Pro** от Google. Рад встрече! Чем могу помочь прямо сейчас — текстом, идеями, вычислениями или генерацией?"
          isWhoAreYou -> "${modePrefix}Я **Gemini** — мультимодальный искусственный интеллект нового поколения от Google. Умею быстро находить ответы, писать код, генерировать идеи и помогать в учёбе и работе."
          isHowAreYou -> "${modePrefix}Отлично, спасибо! Готов творить и решать задачи. О чём поговорим?"
          isThanks -> "${modePrefix}Пожалуйста! Очень рад был помочь. Обращайся в любое время!"
          else -> "${modePrefix}Отличный вопрос по теме **«$originalPrompt»**!\n\nЯ обработал информацию и готов помочь разобраться подробнее. Чем конкретно могу помочь по этой теме?"
        }
      }
      AiModelType.GOOGLE_OMNI_FLASH -> {
        when {
          isGreeting -> "⚡ **Google Omni Flash** активен! Я ультра-скоростная мультимодальная модель Google: обрабатываю текст, изображения, код и аудио со скоростью света. Чем помочь?"
          isWhoAreYou -> "⚡ Я **Google Omni Flash** — передовая мультимодальная нейросеть с поддержкой сквозной обработки речи, фото, видео и сверхбыстрого вывода ответов (<100мс)."
          isHowAreYou -> "⚡ Скорость обработки: 140 токенов/сек. Задержка минимальная. Все аудио-визуальные конвейеры готовы!"
          isThanks -> "⚡ Всегда к вашим услугам! Обращайтесь за быстрыми вычислениями и генерацией."
          else -> "⚡ **Google Omni Flash:**\n\nОбработал ваш запрос «**$originalPrompt**» с максимальным приоритетом. Готов предоставить подробные инструкции или выполнить генерацию прямо сейчас!"
        }
      }
      AiModelType.VEO_3 -> {
        when {
          isGreeting -> "✨ Приветствую в **Google Veo 3 Video Studio**! Я создаю кинематографичные видеоролики 4K 60FPS по текстовому описанию и вашим фотографиям. Опишите сцену или прикрепите фото!"
          isWhoAreYou -> "✨ Я **Google Veo 3** — флагманская диффузионная модель генерации кинематографичного видео от Google DeepMind. Поддерживаю фотореализм, физику движения, кинематографическую камеру и освещение."
          isHowAreYou -> "✨ Все видеокарты рендеринга Veo 3 онлайн. Готов превратить любой сюжет в видео!"
          isThanks -> "✨ Рад был помочь! Создавайте новые шедевры с Google Veo 3!"
          else -> "✨ **Google Veo 3 — Сюжет принят в обработку:**\n\n«$originalPrompt»\n\n🎬 Нажмите **«Контент ИИ»** или введите команду генерации, чтобы начать рендеринг видео с физикой движения и звуком!"
        }
      }
      AiModelType.CHATGPT -> {
        when {
          isGreeting -> "${modePrefix}Здравствуйте! Я **ChatGPT** (GPT-4o). Чем могу быть полезен сегодня?"
          isWhoAreYou -> "${modePrefix}Я **ChatGPT** — ваш персональный ИИ-ассистент. Могу отвечать на вопросы, решать задачи, писать статьи, составлять планы и программировать."
          isHowAreYou -> "${modePrefix}У меня всё отлично! Готов к продуктивной работе."
          isThanks -> "${modePrefix}Не за что! Рад помочь. Если возникнут ещё вопросы — пишите!"
          else -> "${modePrefix}По вашему запросу **«$originalPrompt»**:\n\nГотов помочь! Напишите подробнее, что требуется сделать, и я предоставлю решение."
        }
      }
      AiModelType.CLAUDE -> {
        when {
          isGreeting -> "${modePrefix}Приветствую! Я **Claude**. Готов помочь вам с вдумчивым анализом, написанием текстов или программированием."
          isWhoAreYou -> "${modePrefix}Я **Claude** — модель ИИ, сфокусированная на глубине, безопасности и точности ответов."
          isHowAreYou -> "${modePrefix}Благодарю, всё отлично! Готов к совместной интеллектуальной работе."
          isThanks -> "${modePrefix}Был искренне рад помочь!"
          else -> "${modePrefix}Внимательно изучил ваш запрос **«$originalPrompt»**.\n\nГотов дать развёрнутый ответ. Что именно вас интересует подробнее?"
        }
      }
      AiModelType.SEEDANSE -> {
        "🎬 Приветствую в **Seedanse Video Studio**!\n\nВведите описание сцены, и я создам анимированное видео с движением и озвучкой!"
      }
    }
  }

  // --- MATH ENGINE ---
  data class MathResult(
    val expression: String,
    val answer: String,
    val steps: List<String>
  )

  private fun trySolveMath(prompt: String): MathResult? {
    val clean = prompt
      .replace("реши", "", ignoreCase = true)
      .replace("вычисли", "", ignoreCase = true)
      .replace("сколько будет", "", ignoreCase = true)
      .replace("посчитай", "", ignoreCase = true)
      .replace("чему равно", "", ignoreCase = true)
      .replace("ответ", "", ignoreCase = true)
      .replace("?", "")
      .replace("=", "")
      .replace("×", "*")
      .replace("÷", "/")
      .replace(":", "/")
      .replace(",", ".")
      .trim()

    // 1. Percentage check: "20% от 500"
    val percentRegex = Regex("""(\d+(?:\.\d+)?)\s*%\s*(?:от|of)\s*(\d+(?:\.\d+)?)""", RegexOption.IGNORE_CASE)
    val percentMatch = percentRegex.find(clean)
    if (percentMatch != null) {
      val p = percentMatch.groupValues[1].toDoubleOrNull() ?: return null
      val total = percentMatch.groupValues[2].toDoubleOrNull() ?: return null
      val result = (p / 100.0) * total
      val formattedResult = formatNumber(result)
      return MathResult(
        expression = "$p% от $total",
        answer = formattedResult,
        steps = listOf(
          "1. Переводим проценты в дробь: $p% = ${p / 100.0}",
          "2. Умножаем на базовое число: ${p / 100.0} × $total = $formattedResult"
        )
      )
    }

    // 2. Square root: "корень из 64"
    val sqrtRegex = Regex("""(?:корень из|sqrt\(?)\s*(\d+(?:\.\d+)?)\)?""", RegexOption.IGNORE_CASE)
    val sqrtMatch = sqrtRegex.find(clean)
    if (sqrtMatch != null) {
      val n = sqrtMatch.groupValues[1].toDoubleOrNull() ?: return null
      if (n < 0) return null
      val result = sqrt(n)
      val formattedResult = formatNumber(result)
      return MathResult(
        expression = "√$n",
        answer = formattedResult,
        steps = listOf(
          "1. Вычисляем квадратный корень из $n",
          "2. Так как $formattedResult × $formattedResult = $n, то √$n = $formattedResult"
        )
      )
    }

    // 3. Power: "2^8" or "2 в степени 8"
    val powerRegex = Regex("""(\d+(?:\.\d+)?)\s*(?:\^|\*\*|в степени)\s*(\d+(?:\.\d+)?)""", RegexOption.IGNORE_CASE)
    val powerMatch = powerRegex.find(clean)
    if (powerMatch != null) {
      val base = powerMatch.groupValues[1].toDoubleOrNull() ?: return null
      val exp = powerMatch.groupValues[2].toDoubleOrNull() ?: return null
      val result = base.pow(exp)
      val formattedResult = formatNumber(result)
      return MathResult(
        expression = "$base ^ $exp",
        answer = formattedResult,
        steps = listOf(
          "1. Возводим основание $base в степень $exp",
          "2. $base ^ $exp = $formattedResult"
        )
      )
    }

    // 4. Linear equation: "2x + 4 = 10"
    val eqRegex = Regex("""(\d+)?x\s*([+-])\s*(\d+)\s*=\s*(\d+)""", RegexOption.IGNORE_CASE)
    val eqMatch = eqRegex.find(prompt.replace(" ", ""))
    if (eqMatch != null) {
      val aStr = eqMatch.groupValues[1]
      val a = if (aStr.isEmpty()) 1.0 else aStr.toDoubleOrNull() ?: 1.0
      val sign = eqMatch.groupValues[2]
      val b = eqMatch.groupValues[3].toDoubleOrNull() ?: return null
      val c = eqMatch.groupValues[4].toDoubleOrNull() ?: return null

      val effectiveB = if (sign == "-") -b else b
      val cMinusB = c - effectiveB
      val x = cMinusB / a
      val formattedX = formatNumber(x)

      return MathResult(
        expression = prompt.trim(),
        answer = "x = $formattedX",
        steps = listOf(
          "1. Переносим свободный член в правую часть: ${a}x = $c ${if (effectiveB >= 0) "- $effectiveB" else "+ ${-effectiveB}"} = $cMinusB",
          "2. Делим обе части уравнения на коэффициент $a: x = $cMinusB / $a",
          "3. Получаем корень: x = $formattedX"
        )
      )
    }

    // 5. General Arithmetic Expression (e.g. "2 + 2", "15 * 4 + 10", "100 / 4 - 5")
    if (clean.any { it in "+-*/^" } && clean.any { it.isDigit() }) {
      try {
        val result = evalSimpleExpression(clean)
        if (result != null && !result.isNaN() && !result.isInfinite()) {
          val formatted = formatNumber(result)
          return MathResult(
            expression = clean,
            answer = formatted,
            steps = listOf(
              "1. Анализируем арифметическое выражение: $clean",
              "2. Выполняем действия в соответствии с приоритетом математических операций",
              "3. Получаем точный результат: $formatted"
            )
          )
        }
      } catch (_: Exception) {}
    }

    return null
  }

  private fun evalSimpleExpression(expr: String): Double? {
    val sanitized = expr.replace(" ", "")
    if (!sanitized.all { it.isDigit() || it in "+-*/()." }) return null

    return try {
      val tokens = tokenize(sanitized) ?: return null
      val parser = MathParser(tokens)
      parser.parse()
    } catch (_: Exception) {
      null
    }
  }

  private fun tokenize(expr: String): List<String>? {
    val list = mutableListOf<String>()
    var i = 0
    while (i < expr.length) {
      val c = expr[i]
      when {
        c.isDigit() || c == '.' -> {
          val start = i
          while (i < expr.length && (expr[i].isDigit() || expr[i] == '.')) {
            i++
          }
          list.add(expr.substring(start, i))
          continue
        }
        c in "+-*/()" -> {
          list.add(c.toString())
          i++
        }
        else -> return null
      }
    }
    return list
  }

  private class MathParser(private val tokens: List<String>) {
    private var pos = 0

    fun parse(): Double? {
      val res = parseAddSub()
      return if (pos == tokens.size) res else null
    }

    private fun parseAddSub(): Double {
      var left = parseMulDiv()
      while (pos < tokens.size && (tokens[pos] == "+" || tokens[pos] == "-")) {
        val op = tokens[pos]
        pos++
        val right = parseMulDiv()
        left = if (op == "+") left + right else left - right
      }
      return left
    }

    private fun parseMulDiv(): Double {
      var left = parsePrimary()
      while (pos < tokens.size && (tokens[pos] == "*" || tokens[pos] == "/")) {
        val op = tokens[pos]
        pos++
        val right = parsePrimary()
        left = if (op == "*") left * right else left / right
      }
      return left
    }

    private fun parsePrimary(): Double {
      if (pos >= tokens.size) return 0.0
      val tok = tokens[pos]
      if (tok == "(") {
        pos++
        val res = parseAddSub()
        if (pos < tokens.size && tokens[pos] == ")") pos++
        return res
      }
      if (tok == "-") {
        pos++
        return -parsePrimary()
      }
      if (tok == "+") {
        pos++
        return parsePrimary()
      }
      pos++
      return tok.toDoubleOrNull() ?: 0.0
    }
  }

  private fun formatNumber(d: Double): String {
    return if (d == d.toLong().toDouble()) {
      d.toLong().toString()
    } else {
      String.format(java.util.Locale.US, "%.4f", d).trimEnd('0').trimEnd('.')
    }
  }

  // --- CODE ENGINE ---
  data class CodeSnippet(
    val title: String,
    val language: String,
    val code: String,
    val explanation: String
  )

  private fun tryGenerateCode(clean: String): CodeSnippet? {
    if (!clean.contains("код") && !clean.contains("напиши") && !clean.contains("скрипт") && !clean.contains("функци") && !clean.contains("программ")) {
      return null
    }

    return when {
      "python" in clean || "пайтон" in clean || "питон" in clean -> {
        CodeSnippet(
          title = "Python: Пример программы",
          language = "python",
          code = """
          |def solve_task(items):
          |    # Фильтрация и возведение четных чисел в квадрат
          |    squares = [x ** 2 for x in items if x % 2 == 0]
          |    return squares
          |
          |# Пример использования
          |data = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]
          |result = solve_task(data)
          |print(f"Результат: {result}")
          """.trimMargin(),
          explanation = "Использует генератор списков (List Comprehension) для эффективной фильтрации и преобразования элементов за один проход O(N)."
        )
      }
      "kotlin" in clean || "котлин" in clean -> {
        CodeSnippet(
          title = "Kotlin: Чистая реализация",
          language = "kotlin",
          code = """
          |fun main() {
          |    val numbers = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
          |    
          |    val result = numbers
          |        .filter { it % 2 == 0 }
          |        .map { it * it }
          |        
          |    println("Четные квадраты: ${'$'}result")
          |}
          """.trimMargin(),
          explanation = "Функциональный подход с использованием стандартных функций высшего порядка filter и map."
        )
      }
      "javascript" in clean || "js" in clean || "джаваскрипт" in clean -> {
        CodeSnippet(
          title = "JavaScript: Современное решение ES6+",
          language = "javascript",
          code = """
          |const calculate = (arr) => {
          |  return arr
          |    .filter(n => n % 2 === 0)
          |    .map(n => n ** 2);
          |};
          |
          |const numbers = [1, 2, 3, 4, 5, 6];
          |console.log('Результат:', calculate(numbers));
          """.trimMargin(),
          explanation = "Используются стрелочные функции ES6 и методы массивов filter/map."
        )
      }
      "бот" in clean || "telegram" in clean || "телеграм" in clean -> {
        CodeSnippet(
          title = "Telegram Bot на Python (aiogram 3.x)",
          language = "python",
          code = """
          |import asyncio
          |from aiogram import Bot, Dispatcher, types
          |from aiogram.filters import Command
          |
          |bot = Bot(token="ВАШ_ТОКЕН")
          |dp = Dispatcher()
          |
          |@dp.message(Command("start"))
          |async def start_cmd(message: types.Message):
          |    await message.answer("👋 Привет! Я бот на Aiogram 3. Чем могу помочь?")
          |
          |async def main():
          |    await dp.start_polling(bot)
          |
          |if __name__ == "__main__":
          |    asyncio.run(main())
          """.trimMargin(),
          explanation = "Асинхронный Telegram-бот с обработчиком команды /start с использованием aiogram."
        )
      }
      else -> null
    }
  }

  // --- FACTS & KNOWLEDGE ENGINE ---
  private fun tryAnswerFact(clean: String): String? {
    return when {
      "скорость света" in clean -> {
        "⚡ **Скорость света в вакууме** составляет ровно **299 792 458 м/с** (приблизительно **300 000 км/с**).\n\nОбозначается фундаментальной физической константой c."
      }
      "столица" in clean -> {
        when {
          "росси" in clean || "рф" in clean -> "🏛️ Столица России — город **Москва**."
          "казахстан" in clean -> "🏛️ Столица Казахстана — город **Астана**."
          "франци" in clean -> "🏛️ Столица Франции — город **Париж**."
          "германи" in clean -> "🏛️ Столица Германии — город **Берлин**."
          "сша" in clean || "америк" in clean -> "🏛️ Столица США — город **Вашингтон (округ Колумбия)**."
          "япони" in clean -> "🏛️ Столица Японии — город **Токио**."
          "кита" in clean -> "🏛️ Столица Китая — город **Пекин**."
          "турци" in clean -> "🏛️ Столица Турции — город **Анкара**."
          else -> null
        }
      }
      "формула воды" in clean -> {
        "💧 Химическая формула воды: **H₂O** (два атома водорода и один атом кислорода)."
      }
      "ускорение свободного падения" in clean || "гравитация" in clean -> {
        "🌍 Ускорение свободного падения на поверхности Земли равно примерно **g ≈ 9.80665 м/с²** (для расчетов обычно принимают 9.8 м/с² или 10 м/с²)."
      }
      "число пи" in clean || "pi" in clean -> {
        "🔢 **Число π (Пи)** — математическая константа, выражающая отношение длины окружности к её диаметру:\n\nπ ≈ 3.141592653589793..."
      }
      else -> null
    }
  }
}
