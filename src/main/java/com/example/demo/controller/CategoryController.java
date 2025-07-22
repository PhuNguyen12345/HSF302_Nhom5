package com.example.demo.controller;

import com.example.demo.entity.Category;
import com.example.demo.repository.CategoryRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/admin/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryRepository categoryRepository;

    //  Show category list
    @GetMapping
    public String listCategories(@RequestParam(required = false) String name,
                                 @RequestParam(defaultValue = "0") int page,
                                 Model model) {

        Pageable pageable = PageRequest.of(page, 5); // mỗi trang 5 danh mục
        Page<Category> categoryPage;

        if (name == null || name.isBlank()) {
            categoryPage = categoryRepository.findAll(pageable);
        } else {
            categoryPage = categoryRepository.findByNameContainingIgnoreCase(name, pageable);
        }

        model.addAttribute("categoryPage", categoryPage);
        model.addAttribute("categories", categoryPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", categoryPage.getTotalPages());
        model.addAttribute("name", name); // giữ lại input

        return "admin_test/category-list";
    }


    //  Show category's form
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("category", new Category());
        return "admin_test/category-form";
    }

    //  Save new category
    @PostMapping("/save")
    public String saveCategory(@Valid @ModelAttribute("category") Category category,
                               BindingResult result,
                               Model model) {

        // Check duplicated
        if (category.getId() == null) {
            // add new
            if (categoryRepository.existsByName(category.getName())) {
                result.rejectValue("name", "category.name.duplicate", "Tên danh mục đã tồn tại");
            }
        } else {
            // Update, exclude id
            if (categoryRepository.existsByNameAndIdNot(category.getName(), category.getId())) {
                result.rejectValue("name", "category.name.duplicate", "Tên danh mục đã tồn tại");
            }
        }

        // rollback
        if (result.hasErrors()) {
            return "admin_test/category-form"; // hoặc tên file template thực tế
        }

        // Save if no error
        categoryRepository.save(category);
        return "redirect:/admin/category";
    }

    //  Show edit form
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy category có ID = " + id));
        model.addAttribute("category", category);
        return "admin_test/category-form";
    }

    //  Update category
    @PostMapping("/update")
    public String updateCategory(@ModelAttribute Category category) {
        categoryRepository.save(category);
        return "redirect:/admin/category";
    }

    // Delete category
    @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable Long id) {
        categoryRepository.deleteById(id);
        return "redirect:/admin/category";
    }
}
