package info.laht.yaj_rpc.htpp

import info.laht.yaj_rpc.RpcHandler
import info.laht.yaj_rpc.RpcListParams
import info.laht.yaj_rpc.RpcParams
import info.laht.yaj_rpc.SampleService
import info.laht.yaj_rpc.net.AbstractAsyncRpcClient
import info.laht.yaj_rpc.net.AbstractRpcClient
import info.laht.yaj_rpc.net.RpcServer
import info.laht.yaj_rpc.net.http.RpcHttpClient
import info.laht.yaj_rpc.net.http.RpcHttpServer
import info.laht.yaj_rpc.net.tcp.RpcTcpClient
import info.laht.yaj_rpc.net.tcp.RpcTcpServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.net.ServerSocket
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

class TestHttp {

    lateinit var server: RpcServer
    lateinit var client: AbstractRpcClient

    @Before
    fun setup() {

        val port = ServerSocket(0).use { it.localPort }
        server = RpcHttpServer(RpcHandler(SampleService())).also {
            it.start(port)
        }

        client = RpcHttpClient("localhost", port)

    }

    @After
    fun tearDown() {
        client.close()
        server.stop()
    }

    @Test
    fun test1() {

        client.write("SampleService.greet", RpcListParams("per")).also {
            println("syncronous response=${it.getResult(String::class.java)}")
        }

    }

}