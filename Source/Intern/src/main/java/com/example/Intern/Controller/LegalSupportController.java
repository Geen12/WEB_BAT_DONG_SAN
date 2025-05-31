package com.example.Intern.Controller;

import com.example.Intern.Entity.LegalSupport;
import com.example.Intern.Entity.Property;
import com.example.Intern.Entity.User;
import com.example.Intern.Entity.Verification;
import com.example.Intern.Handle.LegalSupportHandle;
import com.example.Intern.Handle.PropertyHandle;
import com.example.Intern.Repository.UserRepository;
import com.example.Intern.Utility.Enum.STATUS_LEGAL;
import com.example.Intern.Utility.Enum.STATUS_VERIFICATION;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.sql.Timestamp;
import java.util.List;

@Controller
@RequestMapping("/legalsupport")
public class LegalSupportController {

    @Autowired
    private LegalSupportHandle legalSupportHandle;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PropertyHandle propertyHandle;

    @PostMapping("/{legalSupportId}/delete")
    public RedirectView deleteLegalSupport(@PathVariable Long legalSupportId, HttpServletRequest request) {
        // Lấy thông tin người dùng hiện tại
        String username = request.getUserPrincipal().getName();

        // Tìm đối tượng LegalSupport
        LegalSupport legalSupport = legalSupportHandle.findLegalSupportByUserId(legalSupportId);

        if (legalSupport != null) {
            legalSupportHandle.delete(legalSupport);
        }
        return new RedirectView("/admin/legalsupport");
    }

    @PostMapping("/{legalSupportId}/inProcess")
    public RedirectView inProcessLegalSupport(@PathVariable Long legalSupportId, HttpServletRequest request) {
        // Lấy thông tin người dùng hiện tại
        String username = request.getUserPrincipal().getName();
        User legal = userRepository.findUserByUserName(username);

        // Tìm đối tượng LegalSupport
        LegalSupport legalSupport = legalSupportHandle.findLegalSupportByUserId(legalSupportId);

        if (legalSupport != null) {
            legalSupport.setStatus(STATUS_LEGAL.IN_PROGRESS);
            legalSupport.setUser(legal);
            legalSupportHandle.save(legalSupport);
        }
        return new RedirectView("/admin/legalsupport");
    }

    @PostMapping("/{legalSupportId}/completed")
    public RedirectView completedLegalSupport(@PathVariable Long legalSupportId, HttpServletRequest request) {
        // Lấy thông tin người dùng hiện tại
        String username = request.getUserPrincipal().getName();
        User verifier = userRepository.findUserByUserName(username);

        // Tìm đối tượng LegalSupport
        LegalSupport legalSupport = legalSupportHandle.findLegalSupportByUserId(legalSupportId);

        if (legalSupport != null) {
            legalSupport.setStatus(STATUS_LEGAL.COMPLETED);
            legalSupportHandle.save(legalSupport);
        }
        return new RedirectView("/admin/legalsupport");
    }

    @PostMapping("/{legalSupportId}/canceled")
    public RedirectView canceledLegalSupport(@PathVariable Long legalSupportId, HttpServletRequest request) {
        // Lấy thông tin người dùng hiện tại
        String username = request.getUserPrincipal().getName();
        User verifier = userRepository.findUserByUserName(username);

        // Tìm đối tượng LegalSupport
        LegalSupport legalSupport = legalSupportHandle.findLegalSupportByUserId(legalSupportId);

        if (legalSupport != null) {
            legalSupport.setStatus(STATUS_LEGAL.CANCELLED);
            legalSupportHandle.save(legalSupport);
        }
        return new RedirectView("/admin/legalsupport");
    }
}
