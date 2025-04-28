CREATE TABLE area_emergency_logs
(
    id            INT AUTO_INCREMENT NOT NULL,
    area_location VARCHAR(255)       NULL,
    code          VARCHAR(255)       NULL,
    timestamp     datetime           NULL,
    CONSTRAINT pk_area_emergency_logs PRIMARY KEY (id)
);

CREATE TABLE data
(
    id           INT AUTO_INCREMENT NOT NULL,
    data_type_id INT                NOT NULL,
    data         VARCHAR(255)       NULL,
    CONSTRAINT pk_data PRIMARY KEY (id)
);

CREATE TABLE data_types
(
    id            INT AUTO_INCREMENT NOT NULL,
    type_name     VARCHAR(255)       NOT NULL,
    unit          VARCHAR(255)       NULL,
    `description` TEXT               NULL,
    CONSTRAINT pk_data_types PRIMARY KEY (id)
);

CREATE TABLE device_event_log
(
    id        BIGINT AUTO_INCREMENT NOT NULL,
    device_id INT                   NOT NULL,
    event_id  INT                   NOT NULL,
    timestamp datetime              NULL,
    CONSTRAINT pk_device_event_log PRIMARY KEY (id)
);

CREATE TABLE device_events
(
    id             INT AUTO_INCREMENT NOT NULL,
    code           SMALLINT           NULL,
    priority       INT                NULL,
    device_role_id INT                NOT NULL,
    emergency_id   INT                NULL,
    rule           JSON               NULL,
    CONSTRAINT pk_device_events PRIMARY KEY (id)
);

CREATE TABLE device_roles
(
    id            INT AUTO_INCREMENT NOT NULL,
    role_name     VARCHAR(255)       NOT NULL,
    `description` TEXT               NULL,
    CONSTRAINT pk_device_roles PRIMARY KEY (id)
);

CREATE TABLE devices
(
    id             INT AUTO_INCREMENT NOT NULL,
    device_name    VARCHAR(255)       NOT NULL,
    device_role_id INT                NOT NULL,
    location_id    INT                NOT NULL,
    status         VARCHAR(255)       NULL,
    CONSTRAINT pk_devices PRIMARY KEY (id)
);

CREATE TABLE emergencies
(
    id              INT AUTO_INCREMENT NOT NULL,
    code            SMALLINT           NULL,
    name            VARCHAR(255)       NULL,
    required_events JSON               NULL,
    within_seconds  INT                NULL,
    CONSTRAINT pk_emergencies PRIMARY KEY (id)
);

CREATE TABLE emergency_log
(
    id           BIGINT AUTO_INCREMENT NOT NULL,
    device_id    INT                   NOT NULL,
    emergency_id INT                   NOT NULL,
    timestamp    datetime              NULL,
    CONSTRAINT pk_emergency_log PRIMARY KEY (id)
);

CREATE TABLE emergency_plan_commands
(
    id           INT AUTO_INCREMENT NOT NULL,
    emergency_id INT                NOT NULL,
    commands     JSON               NULL,
    warnings     JSON               NULL,
    CONSTRAINT pk_emergency_plan_commands PRIMARY KEY (id)
);

CREATE TABLE inspection_plans
(
    id            INT AUTO_INCREMENT NOT NULL,
    device_id     INT                NOT NULL,
    frequency     VARCHAR(255)       NULL,
    assignee_id   INT                NOT NULL,
    next_due_date date               NULL,
    status        VARCHAR(255)       NULL,
    CONSTRAINT pk_inspection_plans PRIMARY KEY (id)
);

CREATE TABLE inspection_records
(
    id              INT AUTO_INCREMENT NOT NULL,
    plan_id         INT                NOT NULL,
    device_id       INT                NOT NULL,
    inspector_id    INT                NOT NULL,
    check_items     JSON               NOT NULL,
    status          VARCHAR(255)       NULL,
    issues_found    TEXT               NULL,
    inspection_time datetime           NULL,
    CONSTRAINT pk_inspection_records PRIMARY KEY (id)
);

CREATE TABLE locations
(
    id            INT AUTO_INCREMENT NOT NULL,
    location_name VARCHAR(255)       NOT NULL,
    `description` VARCHAR(255)       NULL,
    CONSTRAINT pk_locations PRIMARY KEY (id)
);

CREATE TABLE permissions
(
    permissions_id INT AUTO_INCREMENT NOT NULL,
    name           VARCHAR(100)       NOT NULL,
    `description`  VARCHAR(255)       NULL,
    CONSTRAINT pk_permissions PRIMARY KEY (permissions_id)
);

CREATE TABLE repair_orders
(
    id                INT AUTO_INCREMENT NOT NULL,
    device_id         INT                NOT NULL,
    reporter_id       INT                NULL,
    assignee_id       INT                NOT NULL,
    fault_description TEXT               NULL,
    status            VARCHAR(255)       NULL,
    created_at        datetime           NULL,
    due               datetime           NULL,
    CONSTRAINT pk_repair_orders PRIMARY KEY (id)
);

CREATE TABLE repair_records
(
    id         INT AUTO_INCREMENT NOT NULL,
    order_id   INT                NOT NULL,
    action     TEXT               NULL,
    start_time datetime           NULL,
    end_time   datetime           NULL,
    cost       DECIMAL            NULL,
    parts_used JSON               NULL,
    CONSTRAINT pk_repair_records PRIMARY KEY (id)
);

CREATE TABLE role_permissions
(
    permission_id INT NOT NULL,
    role_id       INT NOT NULL,
    CONSTRAINT pk_role_permissions PRIMARY KEY (permission_id, role_id)
);

CREATE TABLE roles
(
    id            INT AUTO_INCREMENT NOT NULL,
    name          VARCHAR(100)       NOT NULL,
    `description` VARCHAR(255)       NULL,
    CONSTRAINT pk_roles PRIMARY KEY (id)
);

CREATE TABLE spare_parts
(
    id           INT AUTO_INCREMENT NOT NULL,
    part_name    VARCHAR(255)       NOT NULL,
    quantity     INT                NULL,
    min_quantity INT                NULL,
    CONSTRAINT pk_spare_parts PRIMARY KEY (id)
);

CREATE TABLE token
(
    token_id    INT AUTO_INCREMENT NOT NULL,
    user_id     INT                NOT NULL,
    token_value VARCHAR(255)       NOT NULL,
    created_at  datetime           NOT NULL,
    expires_at  datetime           NULL,
    token_type  VARCHAR(255)       NOT NULL,
    status      VARCHAR(255)       NOT NULL,
    CONSTRAINT pk_token PRIMARY KEY (token_id)
);

CREATE TABLE user_roles
(
    user_id INT NOT NULL,
    role_id INT NOT NULL,
    CONSTRAINT pk_user_roles PRIMARY KEY (user_id, role_id)
);

CREATE TABLE users
(
    user_id    INT AUTO_INCREMENT NOT NULL,
    user_name  VARCHAR(100)       NOT NULL,
    account    VARCHAR(255)       NOT NULL,
    password   VARCHAR(255)       NOT NULL,
    created_at datetime           NOT NULL,
    email      VARCHAR(255)       NULL,
    CONSTRAINT pk_users PRIMARY KEY (user_id)
);

ALTER TABLE permissions
    ADD CONSTRAINT uc_permissions_name UNIQUE (name);

ALTER TABLE roles
    ADD CONSTRAINT uc_roles_name UNIQUE (name);

ALTER TABLE token
    ADD CONSTRAINT uc_token_token_value UNIQUE (token_value);

ALTER TABLE data
    ADD CONSTRAINT FK_DATA_ON_DATA_TYPE FOREIGN KEY (data_type_id) REFERENCES data_types (id);

ALTER TABLE devices
    ADD CONSTRAINT FK_DEVICES_ON_DEVICE_ROLE FOREIGN KEY (device_role_id) REFERENCES device_roles (id);

ALTER TABLE devices
    ADD CONSTRAINT FK_DEVICES_ON_LOCATION FOREIGN KEY (location_id) REFERENCES locations (id);

ALTER TABLE device_events
    ADD CONSTRAINT FK_DEVICE_EVENTS_ON_DEVICEROLE FOREIGN KEY (device_role_id) REFERENCES device_roles (id);

ALTER TABLE device_events
    ADD CONSTRAINT FK_DEVICE_EVENTS_ON_EMERGENCY FOREIGN KEY (emergency_id) REFERENCES emergencies (id);

ALTER TABLE device_event_log
    ADD CONSTRAINT FK_DEVICE_EVENT_LOG_ON_DEVICE FOREIGN KEY (device_id) REFERENCES devices (id);

ALTER TABLE device_event_log
    ADD CONSTRAINT FK_DEVICE_EVENT_LOG_ON_EVENT FOREIGN KEY (event_id) REFERENCES device_events (id);

ALTER TABLE emergency_log
    ADD CONSTRAINT FK_EMERGENCY_LOG_ON_DEVICE FOREIGN KEY (device_id) REFERENCES devices (id);

ALTER TABLE emergency_log
    ADD CONSTRAINT FK_EMERGENCY_LOG_ON_EMERGENCY FOREIGN KEY (emergency_id) REFERENCES emergencies (id);

ALTER TABLE emergency_plan_commands
    ADD CONSTRAINT FK_EMERGENCY_PLAN_COMMANDS_ON_EMERGENCY FOREIGN KEY (emergency_id) REFERENCES emergencies (id);

ALTER TABLE inspection_plans
    ADD CONSTRAINT FK_INSPECTION_PLANS_ON_ASSIGNEE FOREIGN KEY (assignee_id) REFERENCES users (user_id);

ALTER TABLE inspection_plans
    ADD CONSTRAINT FK_INSPECTION_PLANS_ON_DEVICE FOREIGN KEY (device_id) REFERENCES devices (id);

ALTER TABLE inspection_records
    ADD CONSTRAINT FK_INSPECTION_RECORDS_ON_DEVICE FOREIGN KEY (device_id) REFERENCES devices (id);

ALTER TABLE inspection_records
    ADD CONSTRAINT FK_INSPECTION_RECORDS_ON_INSPECTOR FOREIGN KEY (inspector_id) REFERENCES users (user_id);

ALTER TABLE inspection_records
    ADD CONSTRAINT FK_INSPECTION_RECORDS_ON_PLAN FOREIGN KEY (plan_id) REFERENCES inspection_plans (id);

ALTER TABLE repair_orders
    ADD CONSTRAINT FK_REPAIR_ORDERS_ON_ASSIGNEE FOREIGN KEY (assignee_id) REFERENCES users (user_id);

ALTER TABLE repair_orders
    ADD CONSTRAINT FK_REPAIR_ORDERS_ON_DEVICE FOREIGN KEY (device_id) REFERENCES devices (id);

ALTER TABLE repair_orders
    ADD CONSTRAINT FK_REPAIR_ORDERS_ON_REPORTER FOREIGN KEY (reporter_id) REFERENCES users (user_id);

ALTER TABLE repair_records
    ADD CONSTRAINT FK_REPAIR_RECORDS_ON_ORDER FOREIGN KEY (order_id) REFERENCES repair_orders (id);

ALTER TABLE token
    ADD CONSTRAINT FK_TOKEN_ON_USER FOREIGN KEY (user_id) REFERENCES users (user_id);

ALTER TABLE user_roles
    ADD CONSTRAINT FK_USER_ROLES_ON_ROLE FOREIGN KEY (role_id) REFERENCES roles (id);

ALTER TABLE user_roles
    ADD CONSTRAINT FK_USER_ROLES_ON_USER FOREIGN KEY (user_id) REFERENCES users (user_id);

ALTER TABLE role_permissions
    ADD CONSTRAINT fk_rolper_on_permission FOREIGN KEY (permission_id) REFERENCES permissions (permissions_id);

ALTER TABLE role_permissions
    ADD CONSTRAINT fk_rolper_on_role FOREIGN KEY (role_id) REFERENCES roles (id);