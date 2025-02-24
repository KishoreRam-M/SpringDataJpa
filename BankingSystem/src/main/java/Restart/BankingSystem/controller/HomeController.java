package Restart.BankingSystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequestMapping("/home")
public class HomeController {

    @GetMapping("/")
    public String toGetWelcome(Model model) {
        model.addAttribute("title", "🌟🌍 WELCOME TO KRM BANK – WHERE YOUR DREAMS MEET OPPORTUNITIES! 🌍🌟");

        model.addAttribute("message", Map.of(
                "greeting", "📢 Dear Valued Client, 🎉✨ A Grand Welcome to KRM BANK! ✨🎉",
                "welcomeNote", "We are absolutely thrilled and honored to have you onboard! 🤩💙",
                "motto", "At KRM BANK, we don't just offer banking services – we offer trust, security, and a brighter financial future for you! 💰🚀"
        ));

        model.addAttribute("whyChooseUs", Map.of(
                "24x7 Banking", "Effortless Banking, Anytime, Anywhere! 🌎💻",
                "Fast & Secure", "Lightning-Fast & Super Secure Transactions! ⚡🔒",
                "Personalized Solutions", "Tailored Financial Solutions Just for YOU! 🎯🏆"
        ));

        model.addAttribute("journey", Map.of(
                "startToday", "Your Journey with KRM BANK Starts Today! 🚀",
                "customerAsFamily", "You are family! 🏡💖"
        ));

        model.addAttribute("thankYou", "💎 Thank You for Trusting KRM BANK! 💎");

        model.addAttribute("contact", Map.of(
                "closingNote", "💙 With Warm Regards,",
                "bankName", "📌 KRM BANK – Empowering Your Financial Future 💡💰🏦",
                "customerSupport", "📞 7530022429 📲📞",
                "email", "📧 thekrmx@gmail.com 💻🌍"
        ));

        return "home";
    }
}
