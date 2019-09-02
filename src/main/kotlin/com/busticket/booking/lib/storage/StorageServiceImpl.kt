package com.busticket.booking.lib.storage

import com.busticket.booking.lib.randomString
import org.springframework.stereotype.Service
import java.io.IOException
import org.springframework.util.FileSystemUtils
import java.net.MalformedURLException
import org.springframework.core.io.UrlResource
import java.nio.file.StandardCopyOption
import org.springframework.web.multipart.MultipartFile
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.Resource
import org.springframework.util.StringUtils
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.stream.Stream


@Service
class StorageServiceImpl @Autowired constructor(private val properties: StorageProperties) : StorageService {
    private val rootLocation = Paths.get(properties.location)

    override fun store(file: MultipartFile): String {
        val filename = StringUtils.cleanPath(file.originalFilename!!)
        init()
        try {
            if (file.isEmpty) {
                throw StorageException("failed_to_store_file", null, arrayOf(filename))
            }

            val extension = filename.substring(filename.lastIndexOf(".") + 1)
            if (!properties.exceptExtension.contains(extension)) {
                throw StorageException("forbidden_extension")
            }
            val randomName = randomString()
            val now = System.currentTimeMillis()
            val trueFileName = StringUtils.cleanPath( "$randomName-$now.$extension")

            file.inputStream.use { inputStream ->
                Files.copy(inputStream, this.rootLocation.resolve(trueFileName),
                        StandardCopyOption.REPLACE_EXISTING)
            }
            return trueFileName
        } catch (e: IOException) {
            throw StorageException("failed_to_store_file", e, arrayOf(filename))
        }

    }

    override fun loadAll(): Stream<Path> {
        try {
            return Files.walk(this.rootLocation, 1)
                    .filter { path -> path != this.rootLocation }
                    .map(this.rootLocation::relativize)
        } catch (e: IOException) {
            throw StorageException("failed_to_read_store_file", e)
        }

    }

    override fun load(filename: String): Path {
        return rootLocation.resolve(filename)
    }

    override fun loadAsResource(filename: String): Resource {
        try {
            val file = load(filename)
            val resource = UrlResource(file.toUri())
            return if (resource.exists() || resource.isReadable) {
                resource
            } else {
                throw StorageFileNotFoundException("failed_to_read_store_file")
            }
        } catch (e: MalformedURLException) {
            throw StorageFileNotFoundException("failed_to_read_store_file", e)
        }

    }

    override fun deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile())
    }

    override fun init() {
        try {
            if (!Files.exists(rootLocation))
                Files.createDirectories(rootLocation)
        } catch (e: IOException) {
            throw StorageException("Could not initialize storage", e)
        }

    }
}