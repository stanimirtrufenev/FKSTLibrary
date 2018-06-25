package com.bookLords.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.bookLords.configuration.BookConfig;
import com.bookLords.model.User;
import com.bookLords.model.daos.UserProfileDAO;
import com.bookLords.model.interfaces.ILogin;

@Controller
@ContextConfiguration(classes = BookConfig.class)
@SessionAttributes("loggedUser")
public class GetAUserController implements ILogin {

	@Autowired
	private UserProfileDAO userDao;

	@RequestMapping(value = "/ShowUser", method = RequestMethod.GET)
	public String search(Model model, HttpServletRequest request) {
		try {
			Integer id = Integer.parseInt(request.getParameter("id"));
			User user = userDao.getUserById(id);
			if (user != null) {
				model.addAttribute("user", user);
			}

			User loggedUser = getCurrentUser(request);
			if (loggedUser != null) {
				if (loggedUser.getFollowedPeople().contains(user)) {
					model.addAttribute("followedUser", "followed");
				}
			}
			return "viewAnotherProfile";
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/Login";
		}
	}

	@RequestMapping(value = "/ShowUser", method = RequestMethod.POST)
	public String followOrUnfollow(Model model, HttpServletRequest request) {
		try {
			Integer followOrUnfollowUserId = Integer.parseInt(request.getParameter("userId"));
			User user = userDao.getUserById(followOrUnfollowUserId);
			User loggedUser = getCurrentUser(request);
			if (loggedUser != null) {
				switch (request.getParameter("followOrUnfollow")) {
				case "follow":
					userDao.follow(loggedUser.getId(), followOrUnfollowUserId);
					System.out.println(user.getFollowers().add(loggedUser));
					for(User usera : user.getFollowers()){
					System.out.println("Followva me : "+usera.getName());
					}
					model.addAttribute("followedUser", "followed");
					break;

				case "unfollow":
					userDao.unfollow(loggedUser.getId(), followOrUnfollowUserId);
				System.out.println(	user.getFollowers().remove(loggedUser));
					for(User usera : user.getFollowers()){
						System.out.println("NE Followva me : "+usera.getName());
						}
					break;
				}
			}
			if (user != null) {
				model.addAttribute("user", user);
			}
			return "viewAnotherProfile";
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/Login";
		}
	}
}
