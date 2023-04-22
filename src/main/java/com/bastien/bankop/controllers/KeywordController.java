package com.bastien.bankop.controllers;

import com.bastien.bankop.entities.Keyword;
import com.bastien.bankop.exceptions.KeywordNameException;
import com.bastien.bankop.exceptions.MalFormedRequestException;
import com.bastien.bankop.exceptions.NoKeywordFoundException;
import com.bastien.bankop.requests.KeywordRequest;
import com.bastien.bankop.services.KeywordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;a

@RestController
@RequestMapping(path = "/api/v1/keywords")
public class KeywordController {

    /**
     * The <code>KeywordService</code> instance used for this controller.
     */
    private final KeywordService keywordService;

    @Autowired
    public KeywordController(@Qualifier("keywordServiceDB") KeywordService keywordService) {
        this.keywordService = keywordService;
    }

    ////////////////////////////////////
    //          Keyword GET           //
    ////////////////////////////////////

    /**
     * Get a <code>Keyword</code> from its name.
     *
     * @param kw The name of the keyword to search for.
     * @return A <code>Keyword</code> with the given name, if it exists.
     * @throws NoKeywordFoundException if no <code>Keyword</code> associated to this name is found.
     */
    @GetMapping(path = "/get/{kw}")
    public Keyword getKeyword(@PathVariable String kw) {
        return this.keywordService.getKeyword(kw);
    }

    /**
     * Get all <code>Keywords</code> from the database.
     *
     * @return A list of all <code>Keywords</code> in the database. Returns an empty list if none are found.
     */
    @GetMapping(path = "/get")
    public List<Keyword> listKeywords() {
        return this.keywordService.listKeywords();
    }

    /**
     * Get all <code>Keywords</code> with the given id as parent.
     *
     * @param idParent The id parent of the <code>Keywords</code>.
     * @return A list of all <code>Keywords</code> with the given id as parent. Returns an empty list if none are found.
     */
    @GetMapping(path = "/get/parent/{idParent}")
    public List<Keyword> listKeywordsFromTableId(@PathVariable Long idParent) {
        return this.keywordService.listKeywordsFromTableId(idParent);
    }

    ///////////////////////////////////////
    //          Keyword CREATE           //
    ///////////////////////////////////////

    /**
     * Insert a new <code>Keyword</code> into the database. Reads the <code>KeywordRequest</code> and validates it. A
     * valid KeywordRequest must contain a name and a parent. Also tests the validity of the name. No tests are
     * performed on the parent id. A keyword may have a parent id that is not in the database.
     *
     * @param request The <code>KeywordRequest</code> used to create a new <code>Keyword</code>.
     * @throws NullPointerException      if the request is null.
     * @throws MalFormedRequestException if the request does not contain a name or a parent id.
     * @throws KeywordNameException      if the name is invalid.
     */
    @PostMapping(path = "/post")
    public void registerKeyword(@RequestBody KeywordRequest request) {
        Objects.requireNonNull(request, "The request is null.");
        String name = request.keyword().orElseThrow(() -> new MalFormedRequestException("keyword."));
        Long idParent = request.idParent().orElseThrow(() -> new MalFormedRequestException("parent id."));

        Keyword keyword = new Keyword();
        keyword.setKeyword(name);
        keyword.setIdParent(idParent);
        this.keywordService.registerKeyword(keyword);
    }

    ///////////////////////////////////////
    //          Keyword DELETE           //
    ///////////////////////////////////////

    /**
     * Delete a <code>Keyword</code> from the database using the <code>KeywordService</code>.
     *
     * @param kw The name of the <code>Keyword</code> to delete.
     */
    @DeleteMapping(path = "/delete/{kw}")
    public void deleteKeyword(@PathVariable String kw) {
        this.keywordService.deleteKeyword(kw);
    }

    /**
     * Delete all the <code>Keywords</code> from the database having the given parent id.
     *
     * @param parentId the id of the table parent containing the <code>Keywords</code> to delete.
     * @throws NoKeywordFoundException if no <code>Keyword</code> is found.
     */
    @DeleteMapping(path = "/delete/from-parent/{parentId}")
    public void deleteKeywordsFromTableId(@PathVariable Long parentId) {
        this.keywordService.deleteKeywordsFromTableId(parentId);
    }

    ///////////////////////////////////////
    //          Keyword UPDATE           //
    ///////////////////////////////////////

    /**
     * Moves the <code>Keywords</code> in the list to the new parent id in parameter. The list is a list of
     * <code>Keyword</code> names (primary key).
     *
     * @param id       The id of the new parent.
     * @param keywords The list of keywords to move.
     * @throws NoKeywordFoundException if no <code>Keyword</code> associated to a name of the list is found.
     */
    @PutMapping(path = "/put/move-to/{id}")
    public void moveKeywords(@PathVariable Long id, @RequestBody List<String> keywords) {
        if (keywords == null || keywords.isEmpty())
            return;

        this.keywordService.moveKeywords(
                id,
                keywords.stream().map(this::getKeyword).toList()
        );
    }
}
