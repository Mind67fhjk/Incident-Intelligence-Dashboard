package com.insa.incidentdashboard.user;

// UserRole የሚለው enum እስካሁን ስላልፈጠርን ስህተት ሊሰጥ ይችላል።
// ይህን ክፍል ስንፈጥር ስህተቱ ይጠፋል።
// ለጊዜው UserRoleን እንደ String አድርገህ መያዝ ትችላለህ ወይም በኋላ ላይ አስተካክለው።
// ለጊዜው እንደዚህ አድርገው:
// public record UserResponse(
//         Long id,
//         String username,
//         String email,
//         String role // ለጊዜው String, በኋላ ወደ UserRole እንቀይረዋለን
// ) {
// }

// UserRoleን አስቀድመህ ከሰራህ (ለምሳሌ እንደ enum) ይህን ኮድ ተጠቀም:
public record UserResponse(
        Long id,
        String username,
        String email,
        UserRole role // UserRole enum ሲፈጠር የሚሰራ
) {
}