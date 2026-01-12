package com.devdooly.skeleton.core.config;

import com.devdooly.skeleton.common.utils.CommonUtils;
import com.devdooly.skeleton.core.admin.ServerAdministrator;;
import com.devdooly.skeleton.core.dto.Check;
import com.devdooly.skeleton.core.dto.Ping;
import com.devdooly.skeleton.core.properties.CoreProperties;
import com.devdooly.skeleton.core.web.AdminHandler;
import com.devdooly.skeleton.core.web.
import com.devdooly.skeleton.core.web.
import com.devdooly.skeleton.core.web.
import com.devdooly.skeleton.core.
import com.devdooly.skeleton.core.

@Configuration
public class CommonWebConfig {
    private static final String apiVersion = "1.0";

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().info(new Info()
                .title("Skeleton APIs")
                .description("Skeleton APIs Specification")
                .version(apiVersion)
        );
    }

    @Bean
    public GroupedOpenApi adminGroupOpenApi() {
        final String[] paths = {"/admin/**"};
        return GroupedOpenApi.builder().group("admin")
                .addOpenApiCustomizer(openApi -> openApi.info(new Info().title("Admin APIs").version(apiVersion)))
                ,pathsToMatch(paths)
                .build();
    }

    @RouterOperations({
        @RouterOperation(
            path = "/admin/alive",
            method = RequestMethod.GET,
            operation = @Operation(
                operationId = "get-admin-alive",
                tags = "admin",
                description = "Check if server alive.",
                responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "503", description = "Server is not alive, which means service is unavailable.")
                }
            )
        ),
        @RouterOperation(
            path = "/admin/ready",
            method = RequestMethod.GET,
            operation = @Operation(
                operationId = "get-admin-ready",
                tags = "admin",
                description = "Check if server ready.",
                responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "503", description = "Server is not ready and need more time to be ready.")
                }
            )
        ),
        @RouterOperation(
            path = "/admin/check",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE,
            operation = @Operation(
                operationId = "get-admin-check",
                tags = "admin",
                description = "Check if server shutdownable.",
                responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = Check.class)))
                }
            )
        ),
        @RouterOperation(
            path = "/admin/ping",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE,
            operation = @Operation(
                operationId = "get-admin-ping",
                tags = "admin",
                description = "Get server ids.",
                responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = Ping.class)))
                }
            )
        ),
        @RouterOperation(
            path = "/admin/drain",
            method = RequestMethod.POST,
            operation = @Operation(
                operationId = "post-admin-drain",
                tags = "admin",
                description = "Drain all processing and set status to 'OUT_OF_SERVICE'.",
                responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "503", description = "Drain request accepted.")
                }
            )
        ),
        @RouterOperation(
            path = "/admin/down",
            method = RequestMethod.POST,
            operation = @Operation(
                operationId = "post-admin-down",
                tags = "admin",
                description = "Drain all processing and set status to 'DOWN'.",
                responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "503", description = "Down request accepted.")
                }
            )
        )
    })
    @Bean


