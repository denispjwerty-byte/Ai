cmake_minimum_required(VERSION 3.22.1)
project(gguflauncher LANGUAGES C CXX)

set(CMAKE_CXX_STANDARD 17)
set(CMAKE_CXX_STANDARD_REQUIRED ON)

# llama.cpp лежит в корне репозитория: <repo>/llama.cpp
get_filename_component(LLAMA_DIR
    "${CMAKE_CURRENT_SOURCE_DIR}/../../../../llama.cpp" ABSOLUTE)

if(NOT EXISTS "${LLAMA_DIR}/CMakeLists.txt")
    message(FATAL_ERROR "llama.cpp не найден в ${LLAMA_DIR}")
endif()

# --- Настройки сборки llama.cpp для Android ---
set(BUILD_SHARED_LIBS OFF CACHE BOOL "" FORCE)    # статически внутрь нашей .so
set(LLAMA_BUILD_COMMON   OFF CACHE BOOL "" FORCE)
set(LLAMA_BUILD_TESTS    OFF CACHE BOOL "" FORCE)
set(LLAMA_BUILD_EXAMPLES OFF CACHE BOOL "" FORCE)
set(LLAMA_BUILD_TOOLS    OFF CACHE BOOL "" FORCE)
set(LLAMA_BUILD_SERVER   OFF CACHE BOOL "" FORCE)
set(LLAMA_CURL           OFF CACHE BOOL "" FORCE)
set(GGML_OPENMP          OFF CACHE BOOL "" FORCE)
set(GGML_NATIVE          OFF CACHE BOOL "" FORCE) # кросс-компиляция: не берём флаги CPU сборочной машины
set(GGML_CPU_ARM_ARCH "armv8.2-a+dotprod" CACHE STRING "" FORCE)

add_subdirectory("${LLAMA_DIR}" "${CMAKE_CURRENT_BINARY_DIR}/llama.cpp")

# --- Наш JNI-мост ---
add_library(gguflauncher SHARED native-lib.cpp)

target_include_directories(gguflauncher PRIVATE
    "${LLAMA_DIR}/include"
    "${LLAMA_DIR}/ggml/include")

target_link_libraries(gguflauncher PRIVATE llama ggml android log)
