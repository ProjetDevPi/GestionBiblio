<?php

namespace DocumentBundle\Tests\Controller;

use Symfony\Bundle\FrameworkBundle\Test\WebTestCase;

class EmpruntControllerTest extends WebTestCase
{
    public function testEmprunter()
    {
        $client = static::createClient();

        $crawler = $client->request('GET', '/Emprunter');
    }

}
