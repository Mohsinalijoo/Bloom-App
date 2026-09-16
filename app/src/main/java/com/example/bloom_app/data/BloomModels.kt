package com.example.bloom_app.data

data class DailyLog(
    val meals: MealsLog = MealsLog(),
    val movementMinutes: Int = 0,
    val waterGlasses: Int = 0,
    val relaxationMinutes: Int = 0,
    val sleepHours: Int = 0,
    val cycleLastPeriodDate: String? = null,
    val symptoms: SymptomsLog = SymptomsLog()
)

data class MealsLog(
    val breakfast: MealChoice? = null,
    val lunch: MealChoice? = null,
    val dinner: MealChoice? = null
) {
    val loggedCount: Int
        get() = listOfNotNull(breakfast, lunch, dinner).size
}

enum class MealChoice(val label: String) {
    LIGHT("Light"), BALANCED("Balanced"), INDULGENT("Indulgent"), SKIPPED("Skipped")
}

data class SymptomsLog(
    val bloating: SymptomLevel? = null,
    val skinAcne: SymptomLevel? = null,
    val mood: MoodLevel? = null,
    val sleepQuality: SleepQualityLevel? = null
)

enum class SymptomLevel(val label: String) {
    NONE("None"), MILD("Mild"), MODERATE("Moderate"), SEVERE("Severe"), FLARE_UP("Flare-up"), CLEAR("Clear")
}
enum class MoodLevel(val label: String) {
    GREAT("Great"), OKAY("Okay"), LOW("Low"), VERY_LOW("Very low")
}
enum class SleepQualityLevel(val label: String) {
    GREAT("Great"), OKAY("Okay"), POOR("Poor"), VERY_POOR("Very poor")
}

data class GoalTargets(
    val mealsTarget: Int = 3,
    val movementMinutes: Int = 30,
    val waterGlasses: Int = 8,
    val relaxationMinutes: Int = 15,
    val sleepHours: Int = 8
)

data class PcosQuestion(val question: String, val answer: String, val category: String)

object PcosQuestions {
    val all = listOf(
        // Diagnosis & basics
        PcosQuestion(
            "What exactly is PCOS?",
            "PCOS (Polycystic Ovary Syndrome) is a hormonal condition that affects roughly 1 in 10 women of reproductive age. Despite the name, it doesn't always involve cysts — it's more about an imbalance in reproductive hormones that can lead to irregular periods, higher androgen levels, and metabolic changes like insulin resistance.",
            "Diagnosis & basics"
        ),
        PcosQuestion(
            "What causes PCOS?",
            "The exact cause isn't fully understood, but research points to a mix of genetics, insulin resistance, and low-grade inflammation. If a close relative has PCOS, your risk is higher. Insulin resistance in particular seems to drive many of the visible symptoms.",
            "Diagnosis & basics"
        ),
        PcosQuestion(
            "How is PCOS actually diagnosed?",
            "Doctors typically use the Rotterdam criteria: you need 2 out of 3 of the following — irregular or absent periods, high androgen levels (from a blood test or physical signs like acne/excess hair), and polycystic ovaries visible on ultrasound. Other conditions are ruled out first.",
            "Diagnosis & basics"
        ),
        PcosQuestion(
            "Is PCOS the same for everyone?",
            "No. PCOS shows up differently in different people. Some have mostly cycle irregularities, others deal with acne and hair changes, and some struggle mostly with weight and insulin. That's why treatment usually needs to be personalized.",
            "Diagnosis & basics"
        ),

        // Symptoms & body
        PcosQuestion(
            "Why are my periods irregular?",
            "Irregular periods happen because ovulation isn't consistent. Without regular ovulation, the hormonal cycle that triggers a period gets disrupted, leading to skipped, unpredictable, or heavier periods.",
            "Symptoms & body"
        ),
        PcosQuestion(
            "Why does weight feel harder to manage?",
            "Insulin resistance makes your body store fat more easily (especially around the middle) and can increase hunger and cravings. It's not a willpower issue — the metabolic environment is genuinely different, which is why food quality and movement matter more than pure calorie counting.",
            "Symptoms & body"
        ),
        PcosQuestion(
            "What is the deal with insulin resistance?",
            "Insulin resistance means your cells respond less efficiently to insulin, so your pancreas makes more of it. High insulin can push the ovaries to produce more androgens, which worsens PCOS symptoms — creating a cycle. Managing blood sugar helps break that loop.",
            "Symptoms & body"
        ),
        PcosQuestion(
            "Is the acne and hair growth from PCOS treatable?",
            "Yes. Because they're driven by higher androgens, treatment options include topical or oral medications, hormonal contraceptives, anti-androgens (like spironolactone), and lifestyle changes that reduce insulin. Results take time — usually 3–6 months to see meaningful change.",
            "Symptoms & body"
        ),

        // Managing it day to day
        PcosQuestion(
            "Can food really make a difference?",
            "Yes — but not in a restrictive way. The goal is steadier blood sugar. That usually means meals with protein, fiber, healthy fats, and slower-digesting carbs. Consistency across the day matters more than any single 'perfect' meal.",
            "Managing it day to day"
        ),
        PcosQuestion(
            "What foods should I eat — and which should I limit?",
            "Focus on: vegetables, legumes, whole grains, lean protein, fish, nuts, olive oil, berries. Limit (not eliminate): ultra-processed snacks, sugary drinks, refined carbs eaten alone, and excessive alcohol. Small consistent swaps beat strict rules.",
            "Managing it day to day"
        ),
        PcosQuestion(
            "How can I manage insulin resistance day to day?",
            "Simple daily habits help: eat protein with each meal, walk after meals (even 10 minutes), get consistent sleep, manage stress, and include strength training 2–3× per week. These small choices compound over weeks and months.",
            "Managing it day to day"
        ),
        PcosQuestion(
            "Will I need medication forever?",
            "Not necessarily. Some people manage PCOS well with lifestyle changes alone. Others benefit from medications like metformin, hormonal contraceptives, or anti-androgens for specific symptoms. Treatment often changes as life stages change (e.g., trying to conceive).",
            "Managing it day to day"
        ),
        PcosQuestion(
            "How does stress fit into all this?",
            "Chronic stress raises cortisol, which can worsen insulin resistance and disrupt the hormonal cycle. Managing stress isn't optional — relaxation, sleep, and gentle movement are genuinely therapeutic for PCOS, not just 'nice to have'.",
            "Managing it day to day"
        ),

        // Looking ahead
        PcosQuestion(
            "Does PCOS affect fertility?",
            "PCOS is a leading cause of infertility because ovulation is irregular — but many people with PCOS conceive naturally or with support. Treatments like ovulation induction (letrozole, clomiphene) and lifestyle changes are often very effective.",
            "Looking ahead"
        ),
        PcosQuestion(
            "What happens if PCOS is left unmanaged?",
            "Long-term, unmanaged PCOS increases the risk of type 2 diabetes, high blood pressure, cholesterol issues, sleep apnea, endometrial changes, and mood conditions. The good news: consistent management significantly lowers these risks.",
            "Looking ahead"
        )
    )
}