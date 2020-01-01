package liveproject.m2k8s.web;

import static org.springframework.web.bind.annotation.RequestMethod.*;

import javax.validation.Valid;

import liveproject.m2k8s.data.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import liveproject.m2k8s.Profile;

@Controller
@RequestMapping("/profile")
public class ProfileController {

  private ProfileRepository profileRepository;

  @Autowired
  public ProfileController(ProfileRepository profileRepository) {
    this.profileRepository = profileRepository;
  }
  
  @RequestMapping(value="/register", method=GET)
  public String showRegistrationForm(Model model) {
    model.addAttribute(new Profile());
    return "registerForm";
  }
  
  @RequestMapping(value="/register", method=POST)
  public String processRegistration(
      @Valid Profile profile,
      Errors errors) {
    if (errors.hasErrors()) {
      return "registerForm";
    }
    
    profileRepository.save(profile);
    return "redirect:/profile/" + profile.getUsername();
  }
  
  @RequestMapping(value="/{username}", method=GET)
  public String showProfile(@PathVariable String username, Model model) {
    Profile profile = profileRepository.findByUsername(username);
    model.addAttribute(profile);
    return "profile";
  }
  
}
