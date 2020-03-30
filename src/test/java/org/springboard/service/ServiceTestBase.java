package org.springboard.service;

import com.github.javafaker.Faker;
import org.apache.commons.lang3.RandomStringUtils;
import org.springboard.entity.AccessToken;
import org.springboard.entity.Listing;
import org.springboard.entity.Product;
import org.springboard.entity.Task;
import org.springboard.entity.User;
import org.springboard.vo.CreateProductVo;
import org.springboard.vo.CreateTaskVo;
import org.springboard.vo.CreateUserVo;
import org.springboard.vo.UpdateProductVo;
import org.springboard.vo.UpdateTaskVo;
import org.springboard.vo.UpdateUserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;


@ComponentScan("org.springboard")
public abstract class ServiceTestBase {

    @Autowired
    private PasswordEncoder passwordEncoder;

    protected static final Integer CASE_COUNT = 3;

    protected Faker faker = new Faker();

    User buildUser() {
        String password = RandomStringUtils.randomAlphanumeric(10);
        return User.builder()
                   .enabled(true)
                   .name(faker.name().fullName())
                   .email(faker.internet().emailAddress())
                   .password(password)
                   .passwordDigest(passwordEncoder.encode(password))
                   .build();
    }

    CreateUserVo buildCreateUserVo() {
        return CreateUserVo.builder()
                           .name(faker.name().fullName())
                           .email(faker.internet().emailAddress())
                           .password(RandomStringUtils.randomAlphanumeric(10))
                           .build();
    }

    UpdateUserVo buildUpdateUserVo() {
        return UpdateUserVo.builder()
                           .email(faker.internet().emailAddress())
                           .name(faker.name().fullName())
                           .build();
    }

    AccessToken buildAccessToken(User user) {
        return AccessToken.builder()
                          .user(user)
                          .token(RandomStringUtils.randomAlphanumeric(32))
                          .expiredAt(LocalDateTime.now().plusDays(1))
                          .build();
    }

    Product buildProduct(User creator) {
        return Product.builder()
                      .creator(creator)
                      .title(faker.book().title())
                      .description(faker.book().publisher())
                      .build();
    }

    CreateProductVo buildCreateProductVo() {
        return CreateProductVo.builder()
                              .title(faker.book().title())
                              .description(faker.book().author())
                              .build();
    }

    UpdateProductVo buildUpdateProductVo() {
        return UpdateProductVo.builder()
                              .title(faker.book().title())
                              .description(faker.book().author())
                              .build();
    }

    Listing buildListing(Product product, User creator) {
        return Listing.builder()
                      .title(faker.book().title())
                      .description(faker.book().author())
                      .project(product)
                      .creator(creator)
                      .build();
    }

    Task buildTask(Listing listing, Task parentTask, User creator) {
        return Task.builder()
                   .title(faker.book().title())
                   .description(faker.book().author())
                   .listing(listing)
                   .parentTask(parentTask)
                   .creator(creator)
                   .build();
    }

    CreateTaskVo buildCreateTaskVo(String listingUuid, String parentTaskUuid) {
        return CreateTaskVo.builder()
                           .title(faker.book().title())
                           .description(faker.book().author())
                           .listingUuid(listingUuid)
                           .parentUuid(parentTaskUuid)
                           .build();
    }

    UpdateTaskVo buildUpdateTaskVo(String listingUuid, String parentTaskUuid, Boolean completed) {
        return UpdateTaskVo.builder()
                           .title(faker.book().title())
                           .description(faker.book().author())
                           .listingUuid(listingUuid)
                           .parentUuid(parentTaskUuid)
                           .completed(completed)
                           .build();
    }

}
