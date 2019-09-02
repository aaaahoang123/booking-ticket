package com.busticket.booking.lib.storage

import com.busticket.booking.lib.rest.RestResponseService
import org.springframework.stereotype.Controller
import org.springframework.http.ResponseEntity
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import org.springframework.web.multipart.MultipartFile
import org.springframework.http.HttpHeaders
import java.util.stream.Collectors
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder
import sun.security.x509.OIDMap.addAttribute
import java.io.IOException
import org.springframework.ui.Model
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.Resource
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*


@Controller
@RequestMapping(value = ["/upload"])
class FileUploadController @Autowired constructor(
        private val storageService: StorageService,
        private val restResponseService: RestResponseService
) {

    @GetMapping("/{filename:.+}")
    @ResponseBody
    fun serveFile(@PathVariable filename: String): ResponseEntity<Resource> {
        val file = storageService.loadAsResource(filename)
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.IMAGE_PNG_VALUE)
                .body<Resource>(file)
    }

    @PostMapping("/")
    fun handleFileUpload(@RequestParam("file") file: MultipartFile): ResponseEntity<Any> {
        val fileName = storageService.store(file)

        return restResponseService.restSuccess(mapOf(
                "path" to fileName
        ))
    }

    @ExceptionHandler(StorageFileNotFoundException::class)
    fun handleStorageFileNotFound(exc: StorageFileNotFoundException): ResponseEntity<*> {
        return ResponseEntity.notFound().build<Any>()
    }
}