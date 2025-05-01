package org.example.shubackend.entity;

import java.util.EnumSet;
import java.util.Set;

/**
 * Enumeration of all fine‑grained permission identifiers recognised by the system.
 * <p>
 * Each constant follows the pattern <b>ENTITY_ACTION</b>, where the action is one of
 * CREATE, READ, UPDATE or DELETE. Use {@link #crudFor(String)} to obtain the full
 * CRUD set for a given entity prefix (e.g. "DEVICES").
 */
public enum PermissionName {
    // ────────────────────────── Area‑level emergency logs ──────────────────────────
    AREA_EMERGENCY_LOGS_CREATE,
    AREA_EMERGENCY_LOGS_READ,
    AREA_EMERGENCY_LOGS_UPDATE,
    AREA_EMERGENCY_LOGS_DELETE,

    // ───────────────────────────────────── Devices ─────────────────────────────────
    DEVICES_CREATE,
    DEVICES_READ,
    DEVICES_UPDATE,
    DEVICES_DELETE,

    // ─────────────────────────────── Device roles ─────────────────────────────────
    DEVICE_ROLES_CREATE,
    DEVICE_ROLES_READ,
    DEVICE_ROLES_UPDATE,
    DEVICE_ROLES_DELETE,

    // ───────────────────────────────── Emergency logs ─────────────────────────────
    EMERGENCY_LOGS_CREATE,
    EMERGENCY_LOGS_READ,
    EMERGENCY_LOGS_UPDATE,
    EMERGENCY_LOGS_DELETE,

    EMERGENCY_CREATE,
    EMERGENCY_READ,
    EMERGENCY_UPDATE,
    EMERGENCY_DELETE,

    // ───────────────────────────── Emergency plan commands ────────────────────────
    EMERGENCY_PLAN_COMMANDS_CREATE,
    EMERGENCY_PLAN_COMMANDS_READ,
    EMERGENCY_PLAN_COMMANDS_UPDATE,
    EMERGENCY_PLAN_COMMANDS_DELETE,


    // ─────────────────────────────── Inspection plans ─────────────────────────────
    INSPECTION_PLANS_CREATE,
    INSPECTION_PLANS_READ,
    INSPECTION_PLANS_UPDATE,
    INSPECTION_PLANS_DELETE,

    // ────────────────────────────── Inspection records ────────────────────────────
    INSPECTION_RECORDS_CREATE,
    INSPECTION_RECORDS_READ,
    INSPECTION_RECORDS_UPDATE,
    INSPECTION_RECORDS_DELETE,

    // ─────────────────────────────────── Locations ────────────────────────────────
    LOCATIONS_CREATE,
    LOCATIONS_READ,
    LOCATIONS_UPDATE,
    LOCATIONS_DELETE,

    // ──────────────────────────────── Repair orders ───────────────────────────────
    REPAIR_ORDERS_CREATE,
    REPAIR_ORDERS_READ,
    REPAIR_ORDERS_UPDATE,
    REPAIR_ORDERS_DELETE,

    // ──────────────────────────────── Repair records ──────────────────────────────
    REPAIR_RECORDS_CREATE,
    REPAIR_RECORDS_READ,
    REPAIR_RECORDS_UPDATE,
    REPAIR_RECORDS_DELETE,

    // ───────────────────────────────────── Roles ───────────────────────────────────
    ROLES_CREATE,
    ROLES_READ,
    ROLES_UPDATE,
    ROLES_DELETE,

    USERS_CREATE,
    USERS_READ,
    USERS_UPDATE,
    USERS_DELETE,

    // ────────────────────────────────── Spare parts ───────────────────────────────
    SPARE_PARTS_CREATE,
    SPARE_PARTS_READ,
    SPARE_PARTS_UPDATE,
    SPARE_PARTS_DELETE,

    // ──────────────────────────────── Meta‑permissions ────────────────────────────
    PERMISSIONS_CREATE,
    PERMISSIONS_READ,
    PERMISSIONS_UPDATE,
    PERMISSIONS_DELETE,

    // ───────────────────────── Device events ─────────────────────────
    DEVICE_EVENTS_CREATE,
    DEVICE_EVENTS_READ,
    DEVICE_EVENTS_UPDATE,
    DEVICE_EVENTS_DELETE,

    // ─────────────────────── Device event logs ───────────────────────
    DEVICE_EVENT_LOGS_CREATE,
    DEVICE_EVENT_LOGS_READ,
    DEVICE_EVENT_LOGS_UPDATE,
    DEVICE_EVENT_LOGS_DELETE;

    /**
     * Convenience helper that returns the four CRUD permissions (CREATE, READ,
     * UPDATE, DELETE) for the supplied entity prefix.
     *
     * @param entity the entity prefix in upper‑snake‑case, e.g. {@code "DEVICES"}
     * @return an {@link EnumSet} containing the four corresponding permissions
     * @throws IllegalArgumentException if any of the four constants is missing
     */
    public static Set<PermissionName> crudFor(String entity) {
        return EnumSet.of(
                valueOf(entity + "_CREATE"),
                valueOf(entity + "_READ"),
                valueOf(entity + "_UPDATE"),
                valueOf(entity + "_DELETE")
        );
    }
}
