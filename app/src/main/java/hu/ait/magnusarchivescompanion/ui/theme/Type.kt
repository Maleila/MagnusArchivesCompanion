package hu.ait.magnusarchivescompanion.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import hu.ait.magnusarchivescompanion.R


val AbhayaLibra = FontFamily(
    Font(R.font.abhayalibre_regular, FontWeight.Normal, FontStyle.Normal),
    Font(R.font.abhayalibre_bold, FontWeight.Bold, FontStyle.Normal)
)
val AlegrayaSans = FontFamily(
    Font(R.font.alegreyasans_regular, FontWeight.Normal, FontStyle.Normal),
    Font(R.font.alegreyasans_bold, FontWeight.Bold, FontStyle.Normal)
)
// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle( //default - descriptions etc
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp,
        color = Color(android.graphics.Color.GRAY)
    ),
    bodyMedium = TextStyle( //same as above but white
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp,
        color = Color(android.graphics.Color.WHITE)
    ),
    titleLarge = TextStyle( //for app name episode screen
        fontFamily = AbhayaLibra,
        fontWeight = FontWeight.Normal,
        fontSize = 45.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp,
        color = Color(0xFF2f8c24)
    ),
    titleMedium = TextStyle( //for contrast on app name
        fontFamily = AlegrayaSans,
        fontWeight = FontWeight.Normal,
        fontSize = 25.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp,
        color = Color(android.graphics.Color.WHITE)
    ),
    titleSmall = TextStyle( //for episode titles on episode screen cards
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp,
        color = Color(android.graphics.Color.BLACK)
    )
)