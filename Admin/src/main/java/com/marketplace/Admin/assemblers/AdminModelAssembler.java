package com.marketplace.Admin.assemblers;

import com.marketplace.Admin.Controller.AdminControllerV2;
import com.marketplace.Admin.Model.AdminUser;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class AdminModelAssembler implements RepresentationModelAssembler<AdminUser, EntityModel<AdminUser>> {

    @Override
    public EntityModel<AdminUser> toModel(AdminUser adminUser) {
        return EntityModel.of(adminUser,
            linkTo(methodOn(AdminControllerV2.class).getAdminUserById(adminUser.getIdAdmin())).withSelfRel(),
            linkTo(methodOn(AdminControllerV2.class).getAllAdminUsersV2()).withRel("Admins")
        );
    }

}
