package app.utilfree.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ApiController {

    /**
     * GET /api/health
     * Health check endpoint — used by cPanel monitoring or uptime tools.
     */
    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> health() {
        return ResponseEntity.ok(Map.of(
            "status",    "UP",
            "app",       "UtilFree Backend",
            "version",   "1.0.0",
            "timestamp", LocalDateTime.now().toString()
        ));
    }

    /**
     * GET /api/tools
     * Returns metadata about all available tools.
     * Useful for sitemap generation and dynamic SEO.
     */
    @GetMapping("/tools")
    public ResponseEntity<List<Map<String, String>>> tools() {
        var tools = List.of(
            Map.of("id","qr",          "label","QR Code Generator",    "path","/?tool=qr",          "description","Generate custom QR codes for URLs, text, Wi-Fi, and vCards"),
            Map.of("id","invoice",     "label","Invoice Generator",     "path","/?tool=invoice",     "description","Create professional PDF invoices — free, instant download"),
            Map.of("id","ascii",       "label","ASCII Converter",       "path","/?tool=ascii",       "description","Convert text to ASCII codes, binary, hexadecimal and back"),
            Map.of("id","morse",       "label","Morse Code Converter",  "path","/?tool=morse",       "description","Encode and decode Morse code with live audio playback"),
            Map.of("id","adrev",       "label","Ad Revenue Calculator", "path","/?tool=adrev",       "description","Estimate monthly ad earnings by niche and traffic volume"),
            Map.of("id","password",    "label","Password Generator",    "path","/?tool=password",    "description","Generate strong, cryptographically secure passwords"),
            Map.of("id","wordcount",   "label","Word Counter",          "path","/?tool=wordcount",   "description","Count words, characters, sentences, and reading time"),
            Map.of("id","unitconv",    "label","Unit Converter",        "path","/?tool=unitconv",    "description","Convert length, weight, temperature, and speed units"),
            Map.of("id","colorpicker", "label","Color Picker",          "path","/?tool=colorpicker", "description","Pick colors and get HEX, RGB, and HSL values instantly"),
            Map.of("id","base64",      "label","Base64 Encoder",        "path","/?tool=base64",      "description","Encode and decode Base64 strings in one click"),
            Map.of("id","tipcalc",     "label","Tip Calculator",        "path","/?tool=tipcalc",     "description","Calculate tips and split bills per person"),
            Map.of("id","ageCalc",     "label","Age Calculator",        "path","/?tool=ageCalc",     "description","Find exact age and days until next birthday")
        );
        return ResponseEntity.ok(tools);
    }
}
