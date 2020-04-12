package org.springboard.service;

import com.github.javafaker.Faker;
import org.apache.commons.lang3.RandomStringUtils;
import org.springboard.constant.PermissionEnum;
import org.springboard.entity.AccessToken;
import org.springboard.entity.Listing;
import org.springboard.entity.Permission;
import org.springboard.entity.Product;
import org.springboard.entity.Role;
import org.springboard.entity.Task;
import org.springboard.entity.User;
import org.springboard.vo.CreateListingVo;
import org.springboard.vo.CreateProductVo;
import org.springboard.vo.CreateTaskVo;
import org.springboard.vo.CreateUserVo;
import org.springboard.vo.UpdateListingVo;
import org.springboard.vo.UpdateProductVo;
import org.springboard.vo.UpdateTaskVo;
import org.springboard.vo.UpdateUserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@ComponentScan("org.springboard")
public abstract class ServiceTestBase {

    @Autowired
    private PasswordEncoder passwordEncoder;

    public static final Integer CASE_COUNT = 3;

    public Faker faker = new Faker();

    public User buildUser() {
        String password = RandomStringUtils.randomAlphanumeric(10);
        return User.builder()
                   .enabled(true)
                   .name(faker.name().fullName())
                   .email(faker.internet().emailAddress())
                   .password(password)
                   .passwordDigest(passwordEncoder.encode(password))
                   .build();
    }

    public CreateUserVo buildCreateUserVo() {
        return CreateUserVo.builder()
                           .name(faker.name().fullName())
                           .email(faker.internet().emailAddress())
                           .password(RandomStringUtils.randomAlphanumeric(10))
                           .build();
    }

    public UpdateUserVo buildUpdateUserVo() {
        return UpdateUserVo.builder()
                           .email(faker.internet().emailAddress())
                           .name(faker.name().fullName())
                           .build();
    }

    public AccessToken buildAccessToken(User user) {
        return AccessToken.builder()
                          .user(user)
                          .token(RandomStringUtils.randomAlphanumeric(32))
                          .expiredAt(ZonedDateTime.now().plusDays(1))
                          .build();
    }

    public Product buildProduct(User creator) {
        return Product.builder()
                      .creator(creator)
                      .title(faker.book().title())
                      .description(faker.book().publisher())
                      .build();
    }

    public CreateProductVo buildCreateProductVo() {
        return CreateProductVo.builder()
                              .title(faker.book().title())
                              .description(faker.book().author())
                              .build();
    }

    public UpdateProductVo buildUpdateProductVo() {
        return UpdateProductVo.builder()
                              .title(faker.book().title())
                              .description(faker.book().author())
                              .build();
    }

    public Listing buildListing(Product product, User creator) {
        return Listing.builder()
                      .title(faker.book().title())
                      .description(faker.book().author())
                      .project(product)
                      .creator(creator)
                      .build();
    }

    public CreateListingVo buildCreateListingVo(UUID productUuid) {
        return CreateListingVo.builder()
                              .productUuid(productUuid)
                              .title(faker.book().title())
                              .description(faker.book().author())
                              .build();
    }

    public UpdateListingVo buildUpdateListingVo() {
        return UpdateListingVo.builder()
                              .title(faker.book().title())
                              .description(faker.book().author())
                              .build();
    }

    public Task buildTask(Listing listing, Task parentTask, User creator) {
        return Task.builder()
                   .title(faker.book().title())
                   .description(faker.book().author())
                   .listing(listing)
                   .parentTask(parentTask)
                   .creator(creator)
                   .build();
    }

    public CreateTaskVo buildCreateTaskVo(UUID listingUuid, UUID parentTaskUuid) {
        return CreateTaskVo.builder()
                           .title(faker.book().title())
                           .description(faker.book().author())
                           .listingUuid(listingUuid)
                           .parentUuid(parentTaskUuid)
                           .build();
    }

    public UpdateTaskVo buildUpdateTaskVo(UUID listingUuid, UUID parentTaskUuid, Boolean completed) {
        return UpdateTaskVo.builder()
                           .title(faker.book().title())
                           .description(faker.book().author())
                           .listingUuid(listingUuid)
                           .parentUuid(parentTaskUuid)
                           .completed(completed)
                           .build();
    }

    public Permission buildPermission(String sourceType, Long sourceId, PermissionEnum code) {
        return Permission.builder()
                         .sourceType(sourceType)
                         .sourceId(sourceId)
                         .code(code)
                         .build();
    }

    public List<Permission> buildPermissions(String sourceType, Long sourceId) {
        return Stream.of(PermissionEnum.values())
                     .map(e -> buildPermission(sourceType, sourceId, e))
                     .collect(Collectors.toList());
    }

    public List<Permission> buildPermissions() {
        return buildPermissions(Product.class.getSimpleName(), new Random().nextLong());
    }

    public Role buildRole(User creator) {
        return Role.builder()
                   .creator(creator)
                   .title(faker.book().title())
                   .description(faker.book().publisher())
                   .build();
    }
}
